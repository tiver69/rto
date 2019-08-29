import axios from "axios";
import {
  GET_ERRORS,
  SAVE_NEW_TICKET,
  COUNT_TICKET_PRICE,
  GET_PASSENGER_PAGE_TICKETS
} from "./types";

export const getPassengerPageTickets = (
  passengerId,
  page,
  isActive
) => async dispatch => {
  const res = await axios.get(
    `/api/ticket/page?passengerId=${passengerId}&page=${page}&isActive=${isActive}`
  );
  dispatch({
    type: GET_PASSENGER_PAGE_TICKETS,
    payload: res.data
  });
};

export const countTicketsPages = (passengerId, isActive) => async dispatch => {
  const res = await axios.get(
    `/api/ticket/page/count?passengerId=${passengerId}&isActive=${isActive}`
  );
  return res.data;
};

export const countTicketPrice = (
  trainId,
  trainCoachId,
  departureStationId,
  destinationStationId
) => async dispatch => {
  const res = await axios.get(
    `/api/ticket/price?trainId=${trainId}&trainCoachId=${trainCoachId}&departureStationId=${departureStationId}&destinationStationId=${destinationStationId}`
  );
  dispatch({
    type: COUNT_TICKET_PRICE,
    payload: res.data
  });
};

export const savePassengerTicket = ticket => async dispatch => {
  if (window.confirm("Do you confirm bying this ticket?")) {
    try {
      const res = await axios.post("/api/ticket/save", ticket);
      dispatch({
        type: GET_ERRORS,
        payload: {}
      });
      dispatch({
        type: SAVE_NEW_TICKET,
        payload: res.data
      });
    } catch (err) {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      });
    }
  }
};
