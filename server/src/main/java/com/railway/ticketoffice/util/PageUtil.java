package com.railway.ticketoffice.util;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    private static final Integer ITEMS_PER_PAGE = 5;

    public static Pageable getPageableFromPageNumber(int pageNumber) throws DataNotFoundException {
        try {
            return PageRequest.of(pageNumber, ITEMS_PER_PAGE);
        } catch (IllegalArgumentException e) {
            throw new DataNotFoundException(String.format("Requested page#%d doesn't exist", pageNumber));
        }
    }

    public static void checkPageBounds(int requestedPage, Page foundPage){
        if (requestedPage >= foundPage.getTotalPages())
            throw new DataNotFoundException(String.format("Requested page#%d doesn't exist", requestedPage));
    }
}
