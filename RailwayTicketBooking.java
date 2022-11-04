import java.util.*;

public class RailwayTicketBooking {

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("..........Railway Ticket Booking..........");
        System.out.println("1.Book Ticket\n2.Cancel\n3.Show available tickets\n4.Show Booked Tickets\n5.Exit\n\n");

        String name;
        int age,id;
        char preference;
        String preferences = "UML";

        Booker booker = new Booker();

        while(true)
        {
            System.out.println("\n\nEnter your option : ");

            switch (scan.nextInt()) {
                case 1:
                System.out.println("Enter your name : ");
                while(!scan.nextLine().isEmpty());
                name = scan.nextLine();

                System.out.println("Enter your age : ");
                age = scan.nextInt();
                
                System.out.println("Enter your preference(U-Upper,L-Lower,M-Middle) : ");
                preference = Character.toUpperCase(scan.next().charAt(0));

                if(!preferences.contains(String.valueOf(preference)))
                {
                    System.out.printf("Prefernce %c is invalid\n",preference);
                    break;
                }
                booker.add(new Passenger(name, age,preference));
                break;

                case 2:
                System.out.println("Enter your name : ");
                name = scan.next();
                System.out.println("Enter your Passenger ID : ");
                id = scan.nextInt();
                booker.cancel(name,id);
                break;
                
                case 4:
                System.out.println("..........Booked Seat Details..........");
                if(booker.getBooked().size()==0)
                {
                    System.out.print("Details Not Available\n");
                    break;
                }
                System.out.println("\n...Confrimed seats...\n");
                for(Passenger passenger:booker.getBooked())
                {
                    System.out.printf("Passenger ID : %d\nPassenger Name : %s\nPassenger Age : %d\nAlloted Preference : %c\n\n",passenger.id,passenger.name,passenger.age,passenger.allotted);
                }
                System.out.println("\n...RAC List...\n");
                for(Passenger passenger:booker.getRAC())
                {
                    System.out.printf("Passenger ID : %d\nPassenger Name : %s\nPassenger Age : %d\nAlloted Preference : %c\n\n",passenger.id,passenger.name,passenger.age,passenger.allotted);
                }
                System.out.println("\n...Waiting List...\n");
                for(Passenger passenger:booker.getWaitingList())
                {
                    System.out.printf("Passenger ID : %d\nPassenger Name : %s\nPassenger Age : %d\nAlloted Preference : %c\n\n",passenger.id,passenger.name,passenger.age,passenger.allotted);
                }
                break;

                case 3:
                System.out.println("..........Available Tickets..........");
                System.out.printf("Seat Type                      vaccancies \n");
                System.out.printf("Free Berth                         %d\n",booker.getFreeSeats());
                System.out.printf("Free Upper Berth                   %d\n",Booker.ALLOTTED_THRESHOLD-Booker.ALLOTTED_U);
                System.out.printf("Free Lower Berth                   %d\n",Booker.ALLOTTED_THRESHOLD-Booker.ALLOTTED_L);
                System.out.printf("Free Middle Berth                  %d\n",Booker.ALLOTTED_THRESHOLD-Booker.ALLOTTED_M);
                System.out.printf("Free RAC                           %d\n",booker.getFreeRAC());
                System.out.printf("Free Waiting List                  %d\n",booker.getWaitingListCount());
                break;

                case 5:
                scan.close();
                System.exit(0);
                break;

                default:
                System.out.println("INVALID INPUT");
            }
        }
    }
}

class Passenger
{
    static int passenger_id = 1;
    String name;
    int age,id;
    char preference,allotted;

    Passenger(String name,int age,char preference)
    {
        this.name = name;
        this.age = age;
        this.preference = preference;
        this.id = passenger_id++;
    }

    void setAllocated(char allocated)
    {
        this.allotted = Character.toUpperCase(allocated);
    }
}

class Booker
{
    char UPPER_PREFERENCE = 'U',LOWER_PREFERENCE = 'L', MIDDLE_PREERENCE = 'M';
    int MAX_BOOTH = 3,MAX_RAC = 1, MAX_WAITING_LIST = 1;
    static int ALLOTTED_U=0,ALLOTTED_M=0,ALLOTTED_L=0,ALLOTTED_THRESHOLD = 1;
    List<Passenger> booked,RAC,waiting_list;

    Booker()
    {
        booked = new ArrayList<>();
        RAC = new ArrayList<>();
        waiting_list = new ArrayList<>();
    }

    int getFreeSeats()
    {
        return MAX_BOOTH-booked.size();
    }

    int getFreeRAC()
    {
        return MAX_RAC-RAC.size();
    }

    int getWaitingListCount()
    {
        return MAX_WAITING_LIST-waiting_list.size();
    }

    List<Passenger> getBooked()
    {
        return booked;
    }

    List<Passenger> getRAC()
    {
        return RAC;
    }

    List<Passenger> getWaitingList()
    {
        return waiting_list;
    }

    void add(Passenger passenger)
    {
        String format = "Preference %c is available.\nSeat booked from the requested preference\n";
        String format2 = "Preference %c is not available\nPreference %c is booked.\n";

        if(booked.size()<MAX_BOOTH)
        {
            if(passenger.preference==UPPER_PREFERENCE && ALLOTTED_U<ALLOTTED_THRESHOLD)
            {
                System.out.printf(format,UPPER_PREFERENCE);
                passenger.allotted = UPPER_PREFERENCE;
                booked.add(passenger);
                ALLOTTED_U++;
            }
            else if(passenger.preference==MIDDLE_PREERENCE && ALLOTTED_M<ALLOTTED_THRESHOLD)
            {
                System.out.printf(format,MIDDLE_PREERENCE);
                passenger.allotted = MIDDLE_PREERENCE;
                booked.add(passenger);
                ALLOTTED_M++;
            }
            else if(passenger.preference==LOWER_PREFERENCE && ALLOTTED_L<ALLOTTED_THRESHOLD)
            {
                System.out.printf(format,LOWER_PREFERENCE);
                passenger.allotted = LOWER_PREFERENCE;
                booked.add(passenger);
                ALLOTTED_L++;
            }
            
            else if(ALLOTTED_U<ALLOTTED_THRESHOLD)
            {
                System.out.printf(format2,passenger.preference,UPPER_PREFERENCE);
                passenger.allotted = UPPER_PREFERENCE;
                booked.add(passenger);
                ALLOTTED_U++;
            }
            else if(ALLOTTED_L<ALLOTTED_THRESHOLD)
            {
                System.out.printf(format2,passenger.preference,LOWER_PREFERENCE);
                passenger.allotted = LOWER_PREFERENCE;
                booked.add(passenger);
                ALLOTTED_L++;
            }
            else   
            {
                System.out.printf(format2,passenger.preference,MIDDLE_PREERENCE);
                passenger.allotted = MIDDLE_PREERENCE;
                booked.add(passenger);
                ALLOTTED_M++;
            }
        }
        else if(RAC.size()<MAX_RAC)
        {
            System.out.printf("Berth is not available\nYou are added in RAC list.");
            RAC.add(passenger);
        }
        else if(waiting_list.size()<MAX_WAITING_LIST)
        {
            System.out.printf("Neither Berth nor RAC is available\nYou are put under waiting list");
            waiting_list.add(passenger);
        }
        else
        {
            System.out.println("Seat not available");
        }
    }

    void cancel(String name,int id)
    {
        for(Passenger passenger:booked)
        {
            if(passenger.id==id)
            {
                booked.remove(passenger);
                
                if(passenger.allotted==UPPER_PREFERENCE)
                {
                    --ALLOTTED_U; 
                }
                else if(passenger.allotted==LOWER_PREFERENCE)
                {
                    ALLOTTED_L--;
                }
                else
                {
                    ALLOTTED_M--;
                }

                System.out.println("Passenger removed from Booked Passengers list");

                if(RAC.size()>0) 
                {
                    add(RAC.get(0));
                    RAC.remove(0);
                    
                    if(waiting_list.size()>0)
                    {
                        Passenger waiter = waiting_list.get(0);
                        waiting_list.remove(waiter);
                        add(waiter);
                    }
                }
                return;
            }
        }
        for(Passenger passenger:RAC)
        {
            if(passenger.id==id){
                RAC.remove(passenger);
                System.out.println("Passenger removed from RAC Passengers list");
                if(waiting_list.size()>0) 
                {
                    add(waiting_list.get(0));
                    waiting_list.remove(0);
                }
                return;
            }
        }

        for(Passenger passenger:waiting_list)
        {
            if(passenger.id==id)
            {
                if(waiting_list.size()>0)
                {
                    waiting_list.remove(passenger);
                }
                System.out.println("Passenger removed from Waiting List");
                return;
            }
        }
        System.out.println("Your Id is not found in any list");
    }
}
