	private static Task parseMsg(String userInput){
			  String description;
		    String stringStartTime, stringEndTime;
		    int[] startTime = new int[5];
		    int[] endTime = new int[5];
		    //edit command
		    if(input1.charAt(2) == '/')
		    {
		    	startTime = convertToInt(stringStartTime);
		    	return Task(startTime[0], startTime[1],startTime[2], startTime[3], startTime[4], -1,-1,-1,-1,null);
		    }
		   // String test = "pick up boiboi from 23/01/2015 23:15 to 24/01/2015 13:14" ;
		    String[] content = test.split("from");
		    description = content[0].trim();
		    content = content[1].split("to");
		    stringStartTime = content[0].trim();
		    stringEndTime = content[1].trim();
		    startTime = convertToInt(stringStartTime);
		    endTime = convertToInt(stringEndTime);
		    return Task(startTime[0], startTime[1],startTime[2], startTime[3], startTime[4], endTime[0], endTime[1],endTime[2], endTime[3], endTime[4], description);		  }
	
	private static int[] convertToInt(String stringTime){
		    int[] intTime = new int[5];
		    String[] content = stringTime.split(" ");
		    String[] time = content[0].split("/");
		    for(int i = 0 ; i < time.length; i++)
		    {
		      intTime[i] = Integer.parseInt(time[i]);
		    }
		    time = content[1].split(":");
		    intTime[3] = Integer.parseInt(time[0]);
		    intTime[4] = Integer.parseInt(time[1]);
		    return intTime;
		} 
