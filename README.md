# Satellite scheduler
Satellite schedule for the ground station communications.

### Description
This program finds a 30-minute period where the total downlink (all satellite passes) will be at its maximum.

### Pre-requisite
- git pull repository
- installed maven
- installed java

### Technologies
- java
- maven

### Input file example
RedDwarf,2,00:00,01:30 = Satellite name, satellite bandwidth, (from - to) pass

# Results
### Solution Description
- (WySpace.java) program reads the input that is passed into a program as an argument and parse it as an integer
- (WySpace.java) timeframe list is initialized with a size of minutes in a day (24 * 60 = 1440)
- (Utils.java) the data from a file (in data/pass-schedule.txt) is read into a HashMap where key presents satellite name and value a satellite properties (bandwidth & start time and end time). Each satellite could have multiple passes which is presented as a list 
- (DownLinkProgram.java) main function then calls DownLinkProgram.findDownLinkMaxRate which finds max subsequence of satellite bandwidth in a range of 30 min. Result is of form Result.class which have a property max downlink and pass (start, end)
- result is printed in a file on a location /data/results.txt and is described bellow

### Example Input
File: pass-schedule.txt 
Input as an argument: 1500 (units per 30 min)
### Example Output
Max bandwidth: 1309 per 30 min
Start time (pass): 15:0
End time (pass): 15:30
The ground station has the bandwidth to support this: Yes