package com.Relearn;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Card[] cardArray = new Card[13];
        Card aceOfHearts = Card.getFaceCard(Card.Suit.HEART, 'A');
        Arrays.fill(cardArray, aceOfHearts);
        Card.printDeck(Arrays.asList(cardArray), "Ace of Hearts", 1);


        //Using the Collection class
        System.out.println("--------------------------------");
        System.out.println("USING THE COLLECTION CLASS");
        List<Card> cards = new ArrayList<>(52);
        Collections.fill(cards, aceOfHearts);
        System.out.println(cards);
        System.out.println("Cards size: " + cards.size());


        List<Card> aceOfHearts2 = Collections.nCopies(13, aceOfHearts);
        Card.printDeck(aceOfHearts2, "Aces of Hearts", 1);

        Card kingOfClubs = Card.getFaceCard(Card.Suit.CLUB, 'K');
        List<Card> kingsOfClubs = Collections.nCopies(13, kingOfClubs); //returns an immutable list consisting of n copies of the specified object
        Card.printDeck(kingsOfClubs, "King of Clubs", 1);

        //copying from one collection to another:
        System.out.println("--------------------------------");
        System.out.println("Copying");
        Collections.addAll(cards, cardArray); //adds all the elements in the second param to the collection in the first param
        Card.printDeck(cards, "Card collection with aces added", 2);

        Collections.copy(cards, kingsOfClubs); //to run this to get it work, our collection must have at least equal number of elements, because they are being replaced, not instantiated
        Card.printDeck(cards, "Card collection with kings copied", 2);

        cards = List.copyOf(kingsOfClubs);
        Card.printDeck(cards, "List Copy of Kings", 2);

        List<Card> deck =  Card.getStandardDeck();
        Card.printDeck(deck);

        //using the shuffle method - useful to randomize a list
        System.out.println("--------------------------------");
        System.out.println("Shuffle");
        Collections.shuffle(deck);
        Card.printDeck(deck, "Shuffled Deck", 4);

        //reversing
        Collections.reverse(deck);
        Card.printDeck(deck, "Reversed Shuffled-Deck", 4);

        //sorting
        //if the element implements Comparable then it will sort it with it, if not, you can pass a comparator
        System.out.println("--------------------------------");
        System.out.println("Sorting");
        var sortingAlgorithm = Comparator.comparing(Card::rank).thenComparing(Card::suit);
        Collections.sort(deck, sortingAlgorithm); //List.sort() is the most used now, but the underlying code calls List.sort() anyway, so..


//        Collections.reverse(deck);
        System.out.println("--------------------------------");
        System.out.println("Reverse");
        Card.printDeck(deck, "Standard Sorted by rank, suit", 11);

        Collections.reverse(deck);
        Card.printDeck(deck, "Standard Sorted by rank, suit (reversed)", 11);

        //sublists
        System.out.println("--------------------------------");
        System.out.println("Sub-lists");
        List<Card> kings = new ArrayList<>(deck.subList(4, 8));
        List<Card> tens = new ArrayList<>(deck.subList(16, 20));

        Card.printDeck(kings, "Kings on deck", 1);
        Card.printDeck(tens, "tens on deck", 1);

//        Collections.shuffle(deck);
        System.out.println("--------------------------------");
        System.out.println("Shuffle");
        int tensSublistIndex = Collections.indexOfSubList(deck, tens);
        System.out.println("Sublist index for tens: " + tensSublistIndex);
        System.out.println("Sublist index for kings: " + Collections.indexOfSubList(deck, kings));

        System.out.println("Contains = " + deck.containsAll(tens));

        //disjoint (bool)- returns true if any two provided elements do not share any elements in common
        System.out.println("--------------------------------");
        System.out.println("Disjoint");
        boolean disjoint = Collections.disjoint(tens, kings);
        boolean joint = Collections.disjoint(deck, tens);

        System.out.println("Kings and Tens? - " + disjoint);
        System.out.println("Tens and Deck? - " + joint);

        //Binary Search - requires the elements to be sorted first, and does not guarantee which index is returned in case of duplicates
        System.out.println("--------------------------------");
        System.out.println("Binary Search");
        deck.sort(sortingAlgorithm);

        Card tenOfHearts = Card.getNumericCard(Card.Suit.HEART, 10);
        // binarySearch(list, key_to_search_for, Comparator)
        int foundIndex = Collections.binarySearch(deck, tenOfHearts, sortingAlgorithm);
        System.out.println("Found index: " + foundIndex);
//      System.out.println("Found index: " + deck.indexOf(tenOfHearts)); //indexOf() -> use when the list is unsorted, contains small number of elements, or contains duplicates
        System.out.println(deck.get(foundIndex));

        //replaceAll() - replaces as many appearances of the specified element as it sees in the list, with the provided element
        System.out.println("--------------------------------");
        System.out.println("Replace all");
        Card tenOfClubs = Card.getNumericCard(Card.Suit.CLUB, 10);

        Collections.replaceAll(deck, tenOfHearts, tenOfClubs);
        Card.printDeck(deck.subList(32, 36), "Tens Row", 1);

        Collections.replaceAll(deck, tenOfClubs, tenOfHearts);
        Card.printDeck(deck.subList(32, 36), "Tens Row", 1);

        //the replaceAll() returns a boolean value. True if any element(s) was replaced, false if not
        if (Collections.replaceAll(deck, tenOfHearts, tenOfClubs)){
            System.out.println("Ten of hearts replaced with ten of clubs");
        }
        else
            System.out.println("No ten of hearts found in the list!");

        //Frequency - checks how many times an element appears
        System.out.println("--------------------------------");
        System.out.println("frequency");
        System.out.println("Tens of Hearts in deck: " + Collections.frequency(deck, tenOfHearts));

        //min and max - returns the first and last elements of a list
        System.out.println("--------------------------------");
        System.out.println("min and max");

        System.out.println("Best Card: " + Collections.max(deck, sortingAlgorithm));
        System.out.println("Worst Card: " + Collections.min(deck, Comparator.comparing(Card::rank).thenComparing(Card::suit)));

        //Rotate - pushes or drags elements from back to front, or front to back, respectively, and appropriately, by the specified distance
        System.out.println("--------------------------------");
        System.out.println("Rotate");

        var sortBySuit = Comparator.comparing(Card::suit).thenComparing(Card::rank);
        deck.sort(sortBySuit);
        Card.printDeck(deck, "Sorted by Suit, then Rank", 4);

        System.out.println();

        List<Card> copied = new ArrayList<>(deck.subList(0, 13));
        Collections.rotate(copied, 3);
        System.out.println("UnRotated: " + deck.subList(0, 13));
        System.out.println("Rotated by " + 3 + " : " + copied);

        copied = new ArrayList<>(deck.subList(0, 13));
        Collections.rotate(copied, -3);
        System.out.println("UnRotated: " + deck.subList(0, 13));
        System.out.println("Rotated by " + -3 + " : " + copied);

        //swap - as the name implies, lets you swap stuff
        System.out.println("--------------------------------");
        System.out.println("Swap");

        copied = new ArrayList<>(deck.subList(0, 13));
        for (int i = 0; i < copied.size() / 2; i++){
            Collections.swap(copied, i, copied.size() - 1 - i);
            //          swap((List<?> list) list, (int i) index_of_element_to_replace, (int j) element_to_be_stored_at_that_specified_position)
        }

        System.out.println("Manual reverse (using swap()): " + copied);
        System.out.println();

        copied = new ArrayList<>(deck.subList(0, 13));
        Collections.reverse(copied);
        System.out.println("using the reverse method: " + copied);

//     -> Java's swap method ->
//        public static void swap(List<?> list, int i, int j) {
//            // instead of using a raw type here, it's possible to capture
//            // the wildcard but it will require a call to a supplementary
//            // private method
//            final List l = list;
//            l.set(i, l.set(j, l.get(i)));
//        }

        //there are other methods for transforming collections into other collection types -> Viewable, Immutable, Checkable, Empty Collections.
        //Many have been replaced by List's default methods, better under most situations
    }
}
