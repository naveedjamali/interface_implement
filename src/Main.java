public class Main {
    public static void main(String[] args) {
        IBook b1 = new Book("Java 1", "Joe");
        IBook b2 = new Book("JavaFX 2", "Bill");
        IBook b3 = new Book("Database", "Mike");

        IBookPile pile = new BookPile();
        pile.add(b1);
        pile.add(b2);
        pile.add(b3);

        for (int i = 0; i < pile.size(); i++) {
            System.out.println(pile.getTitleAt(i));
        }
        System.out.println("Add new book with title: Operating Systems");
        pile.add(new Book("Operating Systems", "Mr Simon"));
        System.out.println("Book at index 3: " + pile.getTitleAt(3));
        System.out.println("Total books in Pile: " + pile.size());


        System.out.println("Get titles of all books, and then print:");
        String[] titles = pile.getTitles();
        for (int i = 0; i < titles.length; i++) {
            System.out.println(titles[i]);
        }

        System.out.println("Pile contains book with title \"Operating Systems\"? " + pile.contains("Operating Systems"));

        System.out.println("Pile contains book with title \"Harry Potter\"? " + pile.contains("Harry Potter"));

        IBook b = pile.remove();
        System.out.println("Removed book " + b.getTitle());

        System.out.println("Pile contains " + b.getTitle() + "? " + pile.contains(b.getTitle()));
        System.out.println("Size of pile: " + pile.size());

        System.out.println("Adding 2 more books with title:" + b3.getTitle());
        pile.add(new Book("Database", "Mike"));
        pile.add(new Book("Database", "Philip"));
        System.out.println("Size of pile: " + pile.size());
        System.out.println("Number of books with title \"" + b3.getTitle() + "\": " + pile.count(b3.getTitle()));

        System.out.println("\nReprinting all titles\n");
        titles = pile.getTitles();
        for (int i = 0; i < titles.length; i++) {
            System.out.println(titles[i]);
        }
    }
}

interface IBook {
    String getTitle();

    IBook getNextBook();

    void setNextBook(IBook book);
}

class Book implements IBook {
    private String title;
    private String author;
    private IBook nextBook;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public IBook getNextBook() {
        return nextBook;
    }

    @Override
    public void setNextBook(IBook book) {
        nextBook = book;
    }

    public String getAuthor() {
        return author;
    }

}

/**
 * A pile of very heavy books. A book is so heavy that
 * only one book can be placed on top of the pile one at a time
 * and only a book can be removed from the top of the pile one
 * at a time. No book can be removed
 */
interface IBookPile {

    /**
     * Place the book on top of the bile of books
     *
     * @param book the book to be placed
     * @return Return true if successful.
     */
    public boolean add(IBook book);


    /**
     * Remove the book from the top of the pile
     *
     * @return book removed
     */
    public IBook remove();

    /**
     * Return the number of books in the pile
     *
     * @return number of books in the pile
     */
    public int size();

    /**
     * Return the number of books that have the title.
     *
     * @param title the title to search
     * @return number of books matching the title
     */
    int count(String title);

    /**
     * Return the titles of all books in the pile
     *
     * @return array of titles
     */
    public String[] getTitles();

    /**
     * Return the title of book at position start from bottom.
     *
     * @param pos position of book
     * @return The title of the book
     */
    public String getTitleAt(int pos);

    /**
     * Return true if pile of books contain such book.
     *
     * @param title the title of the book.
     * @return True if there's a book with the title.
     */
    public boolean contains(String title);


}

class BookPile implements IBookPile {

    int size = 0;
    IBook currentBook;


    public boolean add(IBook book) {
        book.setNextBook(currentBook);
        currentBook = book;
        size++;
        return true;
    }

    public IBook remove() {
        if (currentBook.getNextBook() != null) {
            IBook tempBook = currentBook;
            currentBook = currentBook.getNextBook();
            size--;
            return tempBook;

        }
        return null;

    }

    public int size() {
        return size;
    }

    public int count(String title) {
        int count = 0;
        IBook tempBook = currentBook;
        for (int i = 0; i < size; i++) {
            if (tempBook.getTitle().equals(title)) {
                count++;
            }
            tempBook = tempBook.getNextBook();
        }
        return count;
    }

    public String[] getTitles() {
        String[] titles = new String[size];
        IBook tempBook = currentBook;
        for (int i = 0; i < size; i++) {
            titles[i] = tempBook.getTitle();
            tempBook = tempBook.getNextBook();
        }
        return titles;
    }

    public String getTitleAt(int pos) {
        int booksOnTopCount = size - pos - 1;
        IBook tempBook = currentBook;
        for (int i = 0; i < booksOnTopCount; i++) {
            tempBook = tempBook.getNextBook();
        }
        return tempBook.getTitle();
    }

    public boolean contains(String title) {

        IBook temp = currentBook;
        for (int i = 0; i < size; i++) {
            if (temp.getTitle().equals(title)) {
                return true;
            }
            ;

        }
        return false;
    }
}