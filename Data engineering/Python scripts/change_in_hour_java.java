public static int getDSTDay(int year){

    int[] yearLoop = {29,28,27,26,25,31,30};

    int year = y;

    int numLeapYears = 0;

    int diff = year-1981;

    if(year%4 == 0)
        {numLeapYears = diff/4+1;}
    else
        {numLeapYears = diff/4;}

    int totDiff = diff + numLeapYears;

    int indexLoop = (totDiff - totDiff/7 * 7);

    int day = yearLoop[indexLoop];

    return day;
}



