package com.railway.ticketoffice.util;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static final int ITEMS_PER_PAGE = 5;
    public static String EXIST_MESSAGE_FORMAT = "Requested page#%d doesn't exist";

    public static Pageable getPageableFromPageNumber(int pageNumber) throws DataNotFoundException {
        try {
            return PageRequest.of(pageNumber, ITEMS_PER_PAGE);
        } catch (IllegalArgumentException e) {
            throw new DataNotFoundException(String.format(EXIST_MESSAGE_FORMAT, pageNumber));
        }
    }

    public static void checkPageBounds(int requestedPage, Page foundPage) throws DataNotFoundException {
        if (foundPage.getTotalPages() != 0 && requestedPage >= foundPage.getTotalPages()) {
            throw new DataNotFoundException(String.format(EXIST_MESSAGE_FORMAT, requestedPage));
        }
    }
}
