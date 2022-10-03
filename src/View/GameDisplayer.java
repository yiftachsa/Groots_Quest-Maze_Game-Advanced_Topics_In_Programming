package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.io.FileNotFoundException;

/**
 * Wrapper Class
 * Holds all the displayers used to draw a complete maze game.
 */
public class GameDisplayer {


    private CharacterDisplayer characterDisplayer;
    private MazeDisplayer mazeDisplayer;
    private SolutionDisplayer solutionDisplayer;

    /**
     * Constructor
     * @param mazeDisplayer - MazeDisplayer
     * @param characterDisplayer - CharacterDisplayer
     * @param solutionDisplayer - SolutionDisplayer
     */
    public GameDisplayer(MazeDisplayer mazeDisplayer, CharacterDisplayer characterDisplayer, SolutionDisplayer solutionDisplayer) {
        this.characterDisplayer = characterDisplayer;
        this.mazeDisplayer = mazeDisplayer;
        this.solutionDisplayer = solutionDisplayer;
    }

    /**
     *  Receives a Position and a maze and draws the character in the given position on the maze using characterDisplayer
     * @param characterPosition - Position
     * @param maze - Maze
     */
    public void setCharacterPosition(Position characterPosition, Maze maze ) {
        characterDisplayer.draw(characterPosition, maze.getRowLength(), maze.getColumnLength() );
    }

    /**
     * sets the character position image
     * @param imageFileNameCharacter - String
     */
    public void setCharacterPositionImage( String imageFileNameCharacter ) {
        characterDisplayer.setImageFileNameCharacter(imageFileNameCharacter);
    }

    /**
     * Draws the maze using mazeDisplayer
     * @param maze - Maze
     */
    public void setMaze(Maze maze) {
        solutionDisplayer.clearRectSolution();
        mazeDisplayer.draw(maze);
    }

    /**
     * Draws solution based on a given Solution using solutionDisplayer
     * @param solution
     */
    public void setSolutionDisplayer(Solution solution) {
        solutionDisplayer.draw(solution);
    }

    /**
     * draws the entire game based on the given parameters using the different displayers.
     * @param maze - Maze
     * @param position - Position - character position
     * @param solution - Solution
     */
    public void drawGame(Maze maze, Position position, Solution solution) {

        if (maze != null) {
            mazeDisplayer.draw(maze);
            characterDisplayer.draw(position, maze.getRowLength(), maze.getColumnLength());
            if (solution != null) {
                solutionDisplayer.draw(solution);
            }
        }
    }

    /**
     * returns the base layer displayer height
     * @return - double
     */
    public double getCanvasHeight()
    {
        return mazeDisplayer.getHeight();
    }

    /**
     * returns the base layer displayer width
     * @return - double
     */
    public double getCanvasWidth() {
        return mazeDisplayer.getWidth();
    }
}
