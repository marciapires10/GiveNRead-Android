package pt.ua.givenread;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookInfo {

    @SerializedName("title")
    @Expose
    private String title;

    private String subtitle;

    @SerializedName("authors")
    @Expose
    private ArrayList<String> authors;

    @SerializedName("publisher")
    @Expose
    private String publisher;

    @SerializedName("publishedDate")
    @Expose
    private String publishedDate;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("pageCount")
    @Expose
    private int pageCount;

    @SerializedName("imageLinks")
    @Expose
    private VolumeImageLinks imageLinks;

    @SerializedName("industryIdentifiers")
    @Expose
    private ArrayList<VolumeIndustryIdentifiers> industryIdentifiers;

    private String previewLink;
    private String infoLink;
    private String buyLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public VolumeImageLinks getThumbnail() {
        return imageLinks;
    }

    public void VolumeImageLinks(String thumbnail) {
        this.imageLinks = imageLinks;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public ArrayList<VolumeIndustryIdentifiers> getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(ArrayList<VolumeIndustryIdentifiers> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", imageLinks=" + imageLinks +
                ", industryIdentifiers=" + industryIdentifiers +
                ", previewLink='" + previewLink + '\'' +
                ", infoLink='" + infoLink + '\'' +
                ", buyLink='" + buyLink + '\'' +
                '}';
    }

    public BookInfo() {

    }

    public BookInfo(String title, String subtitle, ArrayList<String> authors, String publisher, String publishedDate, String description, int pageCount, VolumeImageLinks imageLinks, ArrayList<VolumeIndustryIdentifiers> industryIdentifiers, String previewLink, String infoLink, String buyLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.imageLinks = imageLinks;
        this.industryIdentifiers = industryIdentifiers;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.buyLink = buyLink;
    }

    static class BookInfoFirebase{
        @SerializedName("book_title")
        @Expose
        String book_title;

        @SerializedName("authors")
        @Expose
        ArrayList<String> authors;

        @SerializedName("isbn")
        @Expose
        String isbn;

        @SerializedName("bookstop")
        @Expose
        String bookstop;

        public String getBook_title() {
            return book_title;
        }

        public void setBook_title(String book_title) {
            this.book_title = book_title;
        }

        public ArrayList<String> getAuthors() {
            return authors;
        }

        public void setAuthors(ArrayList<String> authors) {
            this.authors = authors;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getBookstop() {
            return bookstop;
        }

        public void setBookstop(String bookstop) {
            this.bookstop = bookstop;
        }

        @Override
        public String toString() {
            return "BookInfoFirebase{" +
                    "book_title='" + book_title + '\'' +
                    ", authors=" + authors +
                    ", isbn='" + isbn + '\'' +
                    ", bookstop='" + bookstop + '\'' +
                    '}';
        }

        public BookInfoFirebase(){

        }

        public BookInfoFirebase(String book_title, ArrayList<String> authors, String isbn, String bookstop) {
            this.book_title = book_title;
            this.authors = authors;
            this.isbn = isbn;
            this.bookstop = bookstop;
        }
    }
}


