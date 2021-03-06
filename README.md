# Smart Home Simulator

This project relates to building a smart home simulator.
The simulator allows logged in users to interact with a house layout and interact with the functionalities of the simulator.

| Team member      | Github username                                     |
|------------------|-----------------------------------------------------|
| Jeff Wilgus      | [@jeffrey-w](https://github.com/jeffrey-w)          |
| Émilie Martin    | [@emilie-martin](https://github.com/emilie-martin)  |
| Philippe Vo      | [@philippeVoNam](https://github.com/philippeVoNam)  |
| Ayman Shehri     | [@AymanShe](https://github.com/AymanShe)            |

---

## Setting up the project
#### json-simple
The project makes use of the external library [json-simple](https://github.com/fangyidong/json-simple).
All that is required to run the project is to download the json-simple-1.1.jar found [here](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm).
  
Then, simply navigate to `Program Files > Java > your JDK folder > bin` and paste the downloaded jar file.
The project should then run seamlessly.

For more information on the json-simple library, take a look at the official json-simple [documentation](https://code.google.com/archive/p/json-simple/).

#### JUnit
This project uses [JUnit](https://junit.org/junit5/) as a testing framework for unit tests. 

JUnit can be added as a Maven dependency. To do so from IntelliJIDEA:
1. Navigate to `File > Project Structure > Libraries`
2. Click the add icon and enter `org.junit.jupiter:junit-jupiter:5.4.2` into the search box.
3. Confirm your selection and save your changes.

#### tinylog
This project uses [tinylog](https://tinylog.org/v2/) for loggings.

tinylog can be added as a Maven dependency. To do so from IntelliJIDEA:
1. Navigate to `File > Project Structure > Libraries`
2. Click the add icon and enter `org.tinylog:tinylog-impl:2.2.0` into the search box.
3. Confirm your selection and save your changes.

---

## Components
#### Parameters
The user running the simulation can change a multitude of parameters.

##### Permission
A simulated user can take on many roles (parent, child, guest, stranger).
Each role has assigned permission, restricting or adding their existing available functionalities.
These roles can be changed and attributed to other users by the user running the simulation.

##### Location
The user running the simulation can change the location of any involved user including themselves, moving them to another room.
It is to be noted that a user cannot be in more than one room, simultaneously.

##### Temperature
There are two temperatures the simulation concerns itself with: indoor and outdoor temperature.
The outdoor temperature pertains to the temperature external to the house layout.
The indoor temperature refers to the temperature of any room contained within the house layout.
Either temperature can be modified by the user running the simulation.

##### Date
A simulated user can change the date of the simulation, moving it forward or backward in time (hours, days, weeks, etc.).
This allows the user to notice the changes in the simulation, based on the time of day and their implemented rules.

#### Doors
Users can interact with doors through the simulation to lock or unlock them.
There should only be up to four doors per room, one per wall (N-E-S-W).

#### Lights
Users can interact with lights through to simulation to turn them on or off.
There is no upper limit to the number of lights that can be in a room.

#### Windows
Users can interact with windows through the simulation to obstruct them.
There should only be up to four windows per room, one per wall (N-E-S-W).

#### Rooms
Rooms contain all the above elements and can be occupied by any number of users.
The user running the simulation will be able to add and remove people from rooms.

#### Heating Zones
A heating zone is an agglomeration of multiple rooms, defined by the user, that share a common temperature.
The user running the simulation can modify the temperature of a heating zone and see it reflected in all affected rooms.
Users can also change the rooms that belong to a specific heating zone.

#### House
A house is a collection of rooms. Its layout is to be displayed to the user through the dashboard.
The user running the simulation will be able to see their changes reflected on the dashboard display.