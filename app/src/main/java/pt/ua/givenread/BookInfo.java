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

    static class BookInfoFirebase{
        @SerializedName("book_title")
        @Expose
        final
        String book_title;

        @SerializedName("authors")
        @Expose
        final
        ArrayList<String> authors;

        @SerializedName("isbn")
        @Expose
        final
        String isbn;

        @SerializedName("bookstop")
        @Expose
        final
        String bookstop;

        @Override
        public String toString() {
            return "BookInfoFirebase{" +
                    "book_title='" + book_title + '\'' +
                    ", authors=" + authors +
                    ", isbn='" + isbn + '\'' +
                    ", bookstop='" + bookstop + '\'' +
                    '}';
        }

        public String getBook_title() {
            return book_title;
        }

        public ArrayList<String> getAuthors() {
            return authors;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getBookstop() {
            return bookstop;
        }

        public BookInfoFirebase(String book_title, ArrayList<String> authors, String isbn, String bookstop) {
            this.book_title = book_title;
            this.authors = authors;
            this.isbn = isbn;
            this.bookstop = bookstop;
        }
    }
}


