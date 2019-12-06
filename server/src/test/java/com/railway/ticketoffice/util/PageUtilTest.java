package com.railway.ticketoffice.util;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

public class PageUtilTest {

    @Test
    public void shouldReturnPageableFromPageNumber() {
        int pageNumber = 3;

        Pageable result = PageUtil.getPageableFromPageNumber(pageNumber);
        Assert.assertEquals(PageUtil.ITEMS_PER_PAGE, result.getPageSize());
        Assert.assertEquals(pageNumber, result.getPageNumber());
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithIncorrectPageNumber() {
        int pageNumber = -3;

        try {
            Pageable result = PageUtil.getPageableFromPageNumber(pageNumber);
        } catch (DataNotFoundException ex) {
            String expectedMessage = String.format(PageUtil.EXIST_MESSAGE_FORMAT, pageNumber);
            Assert.assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void shouldReturnNothingWithCorrectPageBounds() {
        int requestedPage = 3;
        PageImpl page = new PageImpl<>(Collections.emptyList(), PageRequest.of(1, PageUtil.ITEMS_PER_PAGE),
                PageUtil.ITEMS_PER_PAGE * (requestedPage + 2));

        PageUtil.checkPageBounds(requestedPage, page);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithIncorrectPageBounds() {
        int requestedPage = 3;
        PageImpl page = new PageImpl<>(Collections.emptyList(), PageRequest.of(1, PageUtil.ITEMS_PER_PAGE),
                PageUtil.ITEMS_PER_PAGE * (requestedPage - 1));

        try {
            PageUtil.checkPageBounds(requestedPage, page);
        } catch (DataNotFoundException ex) {
            String expectedMessage = String.format(PageUtil.EXIST_MESSAGE_FORMAT, requestedPage);
            Assert.assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }
}