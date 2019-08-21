import axios from "axios";
import { GET_PASSENGER_TICKETS, GET_ERRORS, SAVE_NEW_TICKET } from "./types";

export const getPassengerTickets = passengerId => async dispatch => {
  const res = await axios.get(`/api/ticket?passengerId=${passengerId}`);
  dispatch({
    type: GET_PASSENGER_TICKETS,
    payload: res.data
  });
};

export const savePassengerTicket = (ticket, history) => async dispatch => {
  if (window.confirm("Do you confirm bying this ticket?")) {
    try {
      const res = await axios.post("/api/ticket/save", ticket);
      // history.push(`/home/1`);
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
