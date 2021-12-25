package pogo.responses;

import java.util.Map;
import java.util.Objects;


public class Manufacturers {
    private long  pageSize;
    private int page, totalPageCount;
    private Map<String, String> wkda;

    public Manufacturers(long pageSize, int page, int totalPageCount, Map<String, String> wkda) {
        this.pageSize = pageSize;
        this.page = page;
        this.totalPageCount = totalPageCount;
        this.wkda = wkda;
    }

    public Manufacturers() {}

    public long getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public Map<String, String> getWkda() {
        return wkda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manufacturers)) return false;
        Manufacturers that = (Manufacturers) o;
        return pageSize == that.pageSize && page == that.page && totalPageCount == that.totalPageCount && wkda.equals(that.wkda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageSize, page, totalPageCount, wkda);
    }
}
