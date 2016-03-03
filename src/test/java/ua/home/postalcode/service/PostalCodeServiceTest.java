package ua.home.postalcode.service;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import ua.home.postalcode.data.DistanceResult;
import ua.home.postalcode.data.PostalCode;
import ua.home.postalcode.repository.PostalCodeDao;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostalCodeServiceTest {

    private PostalCodeDao dao;

    @Test
    public void testCalculateDistance() throws Exception {
        PostalCodeService target = createTarget();
        double distance = target.calculateDistance(4.5, -1.1, 3, 1);
        assertEquals(286.5, distance, 0.1);
        distance = target.calculateDistance(1, 10, 1, 11);
        assertEquals(111.2, distance, 0.1);
        distance = target.calculateDistance(1, 0, 2, 0);
        assertEquals(111.2, distance, 0.1);

        verify(dao, never()).update(any(PostalCode.class));
    }

    @Test
    public void testUpdate() throws Exception {
        PostalCodeService target = createTarget();
        PostalCode code = new PostalCode();

        target.update(code);

        verify(dao, times(1)).update(code);
    }

    private PostalCodeService createTarget() {
        PostalCodeService target = new PostalCodeService();
        dao = mock(PostalCodeDao.class);
        Whitebox.setInternalState(target, "postalCodeDao", dao);
        return target;
    }

    @Test
    public void testGetDistance() throws Exception {
        PostalCodeService target = createTarget();
        String postalCodeTextA = "aa11";
        String postalCodeTextB = "bb22";

        when(dao.find(postalCodeTextA)).thenReturn(Optional.of(new PostalCode(postalCodeTextA, 0, 1)));
        when(dao.find(postalCodeTextB)).thenReturn(Optional.of(new PostalCode(postalCodeTextB, 0, 2)));

        DistanceResult result = target.getDistance(postalCodeTextA, postalCodeTextB);

        assertEquals(111.2, result.getDistance(), 0.1);
        assertEquals("km", result.getUnit());
        assertEquals(postalCodeTextA, result.getPostalCodeA().getCode());

        verify(dao, times(1)).find(postalCodeTextA);
        verify(dao, times(1)).find(postalCodeTextB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongCodeA() {
        PostalCodeService target = createTarget();
        String postalCodeTextA = "Wrong code";
        String postalCodeTextB = "bb22";

        when(dao.find(postalCodeTextA)).thenReturn(Optional.empty());
        when(dao.find(postalCodeTextB)).thenReturn(Optional.of(new PostalCode(postalCodeTextB, 0, 2)));

        target.getDistance(postalCodeTextA, postalCodeTextB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongCodeB() {
        PostalCodeService target = createTarget();
        String postalCodeTextA = "aa11";
        String postalCodeTextB = "Wrong code";

        when(dao.find(postalCodeTextA)).thenReturn(Optional.of(new PostalCode(postalCodeTextA, 0, 1)));
        when(dao.find(postalCodeTextB)).thenReturn(Optional.empty());

        target.getDistance(postalCodeTextA, postalCodeTextB);
    }
}