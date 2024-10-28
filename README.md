## Trams

Original description [Polish]:
https://moodle.mimuw.edu.pl/pluginfile.php/299774/mod_assign/introattachment/0/Info.PO.23-24.Pierwsze%20du%C5%BCe%20zadanie%20domowe.pdf?forcedownload=1

Shorter version:

### Task:
Design a basic city traffic simulation in Java using object-oriented principles. Ensure modularity for potential extensions.

#### Simulation elements:

* Each day, passengers head to a stop at random times and attempt to board any available tram. If the stop is full, they forego the trip.
* Represented solely by trams, each with a unique side number and an assigned route.
* Each line has a route composed of stops, with designated loop waiting times.
* Named and capacity-limited stops where passengers await trams.


#### Simulation flow:

* Trams begin routes at 6:00 AM and conclude trips after 11:00 PM.
* Trams travel round-trip on their routes. Each line dispatches half of its trams from one end, the other half from the opposite end.
* Passengers try to board trams and, if unable, wait for the next one.

#### Expected outputs:

* Input parameters, such as the simulation duration in days.
* Detailed event log, with a timestamp and description of each event.
* Simulation statistics, including total passenger trips and average waiting times.

#### Simulation organization:

An event queue structure (e.g., heap, list) will store events in chronological order. Events are processed sequentially each day until empty.

#### Input data: 

Simulation parameters are read from standard input, including days of simulation, stop and tram capacities, and detailed tram route information.