This repository contains an implementation of Wumpus World as well as multiple simple AI agents in Java. The Wumpus
World is a classic problem in artificial intelligence and is often used as a benchmark for testing AI algorithms. The
goal of the game is to navigate a grid-like environment, avoid hazards, and retrieve the gold while avoiding the Wumpus
monster.

# Description of Wumpus World

### Overview

The Wumpus World contains a 4x4 grid of rooms filled with four elements: the agent, the Wumpus, the gold, and pits.

- The agent is the player that navigates the grid with orthogonal movements. The objective is to maximize the score.
- The Wumpus is a lethal monster, but its smell spreads to orthogonally adjacent rooms.
- The gold is a valuable item that the agent can collect and bring back for points.
- Pits are also lethal, but their presence is indicated by a breeze in orthogonally adjacent rooms.

Notably, only the presence of the Wumpus and the pit is indicated, neither the room which contain them nor the amount
that is present in adjacent rooms.

Additionally, the agent can shoot the Wumpus with an arrow, but it can only be done once per game and deducts score. The
arrow travels in an orthogonal direction from the agent's current position indefinitely. The agent can sense whether the
arrow hits the Wumpus or not, but cannot sense the position where it hits.

### Scoring

The agent starts with a score of 0. The score is updated as follows:

- Entering a room with the Wumpus: -1000 points and ends the game.
- Entering a room with a pit: -1000 points and ends the game.
- Making a move to an orthogonally adjacent room: -1 point.
- Shooting an arrow: -10 points.
- Returning to the starting room with the gold: +1000 points.

Notably, there are no points for:

- Turning (which is not implemented).
- Entering a room with the gold. You have to return safely to the starting room with the gold to get points.
- Hitting or missing the Wumpus. There is no reward for hitting the Wumpus, and the 10 point deduction still stands.

### Generation

The Wumpus World is generated with the agent at the top left corner (the origin), exactly one Wumpus and one gold, and
an arbitrary amount of pits (up to a maximum of 13). The procedure which we generate the Wumpus World is as follows:

- Each cell except the origin is independently populated with a pit with probability 20%.
- We do a check on the number of pits. If it is 14 or higher, we regenerate.
- We repeatedly place the Wumpus on a random square until it lands on an unoccupied square.
- We repeatedly place the gold on a random square until it lands on an unoccupied square.

# Implementation Specification


