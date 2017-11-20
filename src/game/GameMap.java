package game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to store the information about the current map - including the terrain, items, and creatures present.
 */
public class GameMap implements GameMapInterface {
	/**
	 * Stores the map tiles in a row-major 2D Tile array.  With (0, 0) at top left, point (col x, row y) is at
	 * map[row y][col x]
	 */
	private Tile[][] map;
	private Player player;
	private List<Monster> monsters;

	/**
	 * Creates a GameMap from a given File.  The file must have lines of uniform length.
	 *
	 * @param file The File to build the map from
	 */
	public GameMap(File file) {
		buildMapFromFile(file);
		this.monsters = new ArrayList<>();
	}


	/**
	 * Creates a GameMap from a given 2D row-major Tile array.  Tells all Entities in the GameMap their coordinates.
	 * @param map 2D row-major Tile array to set as the GameMap's map
	 */
	public GameMap(Tile[][] map) {
		this.map = map;
		monsters = new ArrayList<>();

		int expectedWidth = map[0].length;

		for (int row = 0; row < map.length; row++) {
			if (expectedWidth != map[row].length) {
				throw new IllegalArgumentException("Given Tile array is not rectangular.");
			}

			for (int col = 0; col < map[0].length; col++) {
				for (InventoryItem item : map[row][col].getItems()) {
					item.setCoordinates(new Coordinates(col, row));
					item.setMap(this);
				}

				Creature creature = map[row][col].getCreature();
				if (creature != null) {
					creature.setCoordinates(new Coordinates(col, row));
					creature.setMap(this);
					if (Monster.class.isAssignableFrom(creature.getClass())) {
						monsters.add((Monster) creature);
					}
				}
			}
		}
	}

	/**
	 * Builds the map from the given File.  The file must have lines of uniform length.
	 *
	 * @param file The File to build the map from
	 * @return Whether the GameMap was built from the File successfully
	 */
	private boolean buildMapFromFile(File file) {
		if (map != null) {
			throw new IllegalStateException("Cannot build GameMap from a file when the map already exists.");
		}

		int height = getFileLength(file);
		int width = getFileWidth(file);

		if (height == 0) {
			throw new IllegalArgumentException("Invalid input file - height is 0 (file " + file);
		} else if (width == 0) {
			throw new IllegalArgumentException("Invalid input file - width is 0 (file " + file);
		} else {
			map = new Tile[height][width];

			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				for (int row = 0; row < height; row++) {
					String currentLine = reader.readLine();
					if (currentLine.length() != width) {
						throw new IllegalArgumentException(
								"Line " + row + " in file " + file + "has incorrect width (expected " + width + " " +
								"but was " + currentLine.length() + ")");
					} else {
						for (int col = 0; col < currentLine.length(); col++) {
							Terrain terrain = new Terrain(currentLine.charAt(col));
							Tile tile = new Tile(terrain);
							map[row][col] = tile;
						}
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File not found", e);
			} catch (IOException e) {
				throw new IllegalArgumentException("IOException", e);
			}
		}
	}

	/**
	 * Prints out the map to the console.
	 */
	public void printMapToConsole() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				System.out.print(map[row][col].getMapGraphic());
			}
			System.out.println();
		}
	}

	/**
	 * Returns the current view of the GameMap as a row-major 2D char array.  With (0, 0) at top left, point (col x,
	 * row y) is at map[row y][col x].  This only displays the 'top' of each Tile.
	 *
	 * @return the current view of the GameMap as a row-major 2D char array
	 */
	@Override
	public char[][] getMapAsCharArray() {
		char[][] array = new char[map.length][map[0].length];

		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				array[row][col] = map[row][col].getMapGraphic();
			}
		}

		return array;
	}

	/**
	 * Returns the square area around the given location as a row-major 2D Tile array.  Distance is how many blocks
	 * away from the coordinates to include.  Tiles outside the GameMap are added as null
	 *
	 * @param coordinates Location to get the map around
	 * @param distance    Distance away from the location to include
	 * @return A row-major 2D Tile array centered at the given location.
	 */
	@Override
	public Tile[][] getSquareAreaAroundLocation(Coordinates coordinates, int distance) {
		if (distance < 0) {
			throw new IllegalArgumentException("Cannot get square area with distance < 0");
		}

		Tile[][] results = getRectangularAreaAroundLocation(coordinates, distance, distance);

		return results;
	}

	/**
	 * Returns the square area around the given location as a row-major 2D char array, with what should be visible at
	 * each Tile.
	 *
	 * @param coordinates Location to get the map around
	 * @param distance    Distance away from the location to include
	 * @return A row-major 2D char array centered at the given location.
	 * @see #getSquareAreaAroundLocation(Coordinates, int)
	 */
	public char[][] getSquareAreaAroundLocationAsCharArray(Coordinates coordinates, int distance) {
		return convertTileArrayToCharArray(getSquareAreaAroundLocation(coordinates, distance));
	}

	/**
	 * Returns the rectangular area around the given location as a row-major 2D Tile array.  Width and height are how
	 * many blocks away from the coordinates to include.  Tiles outside the GameMap are added as null.
	 *
	 * @param coordinates Location to get the map around.
	 * @param width       Distance away from the location in the X direction to include
	 * @param height      Distance away from the location in the Y direction to include
	 * @return A row-major 2D Tile array centered at the given location.
	 */
	@Override
	public Tile[][] getRectangularAreaAroundLocation(Coordinates coordinates, int width, int height) {
		if (width < 0) {
			throw new IllegalArgumentException("Cannot get rectangular area with width < 0");
		}
		if (height < 0) {
			throw new IllegalArgumentException("Cannot get rectangular area with height < 0");
		}

		Tile[][] results = new Tile[2 * height + 1][2 * width + 1];

		for (int resultsRow = 0, mapRow = coordinates.getY() - height; resultsRow < 2 * height + 1;
			 resultsRow++, mapRow++) {
			for (int resultsCol = 0, mapCol = coordinates.getX() - width; resultsCol < 2 * width + 1;
				 resultsCol++, mapCol++) {
				if (mapRow < map.length && mapRow >= 0 && mapCol < map[0].length && mapCol >= 0) {
					results[resultsRow][resultsCol] = map[mapRow][mapCol];
				} else {
					results[resultsRow][resultsCol] = null;
				}
			}
		}

		return results;
	}

	/**
	 * Returns the rectangular area around the given location as a row-major 2D char array, with what should be
	 * visible at each Tile.
	 *
	 * @param coordinates The location to get the area around.
	 * @param width       How far horizontally to include.
	 * @param height      How far vertically to include.
	 * @return A row-major 2D char array centered at the given location.
	 * @see #getRectangularAreaAroundLocation(Coordinates, int, int)
	 */
	public char[][] getRectangularAreaAroundLocationAsCharArray(Coordinates coordinates, int width, int height) {
		return convertTileArrayToCharArray(getRectangularAreaAroundLocation(coordinates, width, height));
	}

	/**
	 * Returns the circular area around the given location in a row-major 2D Tile array.
	 * <p>
	 * The returned array is in fact square, with important cases (set in this order):<ul>
	 * <li>A Tile inside the returned array that is outside the GameMap boundaries is set to null.</li>
	 * <li>A Tile inside the returned array that is not visible with the given radius (e.g. top left) is set as new
	 * Tile (new Terrain("notVisible", Terrain.getUnknownTerrainGraphic()))</li>
	 * </ul>
	 *
	 * @param coordinates Location to get the map around
	 * @param radius      Radius away from the location to include
	 * @return A row-major 2D Tile array with visible tiles centered at the given location.
	 */
	@Override
	public Tile[][] getCircularAreaAroundLocation(Coordinates coordinates, int radius) {
		if (radius < 0) {
			throw new IllegalArgumentException("Cannot get circular area with radius < 0");
		}

		Tile[][] results = new Tile[2 * radius + 1][2 * radius + 1];

		for (int resultsRow = 0, mapRow = coordinates.getY() - radius; resultsRow < 2 * radius + 1;
			 resultsRow++, mapRow++) {
			for (int resultsCol = 0, mapCol = coordinates.getX() - radius; resultsCol < 2 * radius + 1;
				 resultsCol++, mapCol++) {
				double currentRadius = Math.sqrt(
						Math.pow(mapRow - coordinates.getY(), 2) + Math.pow(mapCol - coordinates.getX(), 2));
				if ((int) (currentRadius + 0.5) <= radius) {
					// Current location is 'visible', inside radius
					if (mapRow < map.length && mapRow >= 0 && mapCol < map[0].length && mapCol >= 0) {
						results[resultsRow][resultsCol] = map[mapRow][mapCol];
					} else {
						results[resultsRow][resultsCol] = null;
					}
				} else {
					// Current location is not 'visible', outside radius
					if (mapRow < map.length && mapRow >= 0 && mapCol < map[0].length && mapCol >= 0) {
						results[resultsRow][resultsCol] = new Tile(
								new Terrain("notVisible", Terrain.getUnknownTerrainGraphic()));
					} else {
						results[resultsRow][resultsCol] = null;
					}
				}
			}
		}

		return results;
	}

	/**
	 * Returns the circular area around the given location in a row-major 2D char array, with what should be visible
	 * at each Tile.
	 * <p>
	 * The returned array is in fact square, with important cases (set in this order):<ul>
	 * <li>A Tile inside the returned array that is outside the GameMap boundaries is returned as Terrain
	 * .getOutOfWorldTerrainGraphic()</li>
	 * <li>A Tile inside the returned array that is not visible with the given radius (e.g. top left) is returned as
	 * Terrain.getUnknownTerrainGraphic()</li>
	 * </ul>
	 *
	 * @param coordinates Location to get the map around
	 * @param radius      Radius away from the location to include
	 * @return A row-major 2D Tile array with visible tiles centered at the given location.
	 * @see #getCircularAreaAroundLocation(Coordinates, int)
	 */
	public char[][] getCircularAreaAroundLocationAsCharArray(Coordinates coordinates, int radius) {
		return convertTileArrayToCharArray(getCircularAreaAroundLocation(coordinates, radius));
	}

	public char[][] getVisionAsCharArray(Coordinates coordinates, int visionRadius) {
		final int windowHeight = 10;
		final int windowWidth = 30;

		Tile.markTileVisiblity(map, false);

		Tile[][] center = getCircularAreaAroundLocation(coordinates, visionRadius);
		Tile.markTileSeen(center, true);
		Tile.markTileVisiblity(center, true);

		Tile[][] background = getRectangularAreaAroundLocation(coordinates, windowWidth, windowHeight);

		char[][] output = convertTileArrayToCharArray(background);

		return output;
	}

	@Override
	public Tile getTileAtLocation(Coordinates coordinates) {
		if (coordinates.getX() > map[0].length) {
			// TODO figure out how to handle
			throw new IllegalArgumentException("X coordinate too large");
		}
		if (coordinates.getY() > map.length) {
			// TODO figure out how to handle
			throw new IllegalArgumentException("Y coordinate too large");
		}

		return map[coordinates.getY()][coordinates.getX()];
	}

	public Player getPlayer() {
		return player;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}


	public void setPlayer(Player player) {
		this.player = player;

		Tile location = map[player.getCoordinates().getY()][player.getCoordinates().getX()];
		if (!location.addEntity(player)) {
			throw new IllegalStateException(
					"Cannot add a player to a Tile that already has a Creature (location x:" + player.getCoordinates()
							.getX() + ", y:" + player.getCoordinates().getY() + ")");
		}
	}

	public void setMonster(Monster monster) {
		this.monsters.add(monster);

		Tile location = map[monster.getCoordinates().getY()][monster.getCoordinates().getX()];
		if (!location.addEntity(monster)) {
			throw new IllegalStateException(
					"Cannot add a player to a Tile that already has a Creature (location x:" + player.getCoordinates()
							.getX() + ", y:" + monster.getCoordinates().getY() + ")");
		}
	}

	public void removeMonster(Creature monster) {this.monsters.remove(monster);
	}
	/**
	 * Converts a given 2D Tile array to it's character equivalent.  Replaces null Tiles (out of world) with the value
	 * of Terrain.getOutOfWorldTerrainGraphic()
	 *
	 * @param array 2D Tile array to convert to 2D char array
	 * @return 2D char array version of the given 2D Tile array
	 */
	public static char[][] convertTileArrayToCharArray(Tile[][] array) {
		char[][] output = new char[array.length][array[0].length];

		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array[0].length; col++) {
				try {
					output[row][col] = array[row][col].getMapGraphic();
				} catch (NullPointerException e) {
					output[row][col] = Terrain.getOutOfWorldTerrainGraphic();
				}
			}
		}

		return output;
	}

	/**
	 * Gets the height of the given file.
	 *
	 * @param file File to get the height of
	 * @return Height of the given file
	 */
	private int getFileLength(File file) {
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			//noinspection StatementWithEmptyBody
			while (reader.readLine() != null) {
			}
			return reader.getLineNumber();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the width of the first line of the given file.
	 *
	 * @param file File to get the width of
	 * @return Width of the given file
	 */
	private int getFileWidth(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			return br.readLine().length();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
