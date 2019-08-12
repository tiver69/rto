import axios from "axios";
import { GET_TICKETS, GET_PASSENGER_TICKETS } from "./types";

export const getTickets = () => async dispatch => {
  const res = await axios.get("http://localhost:8080/api/ticket/all");
  dispatch({
    type: GET_TICKETS,
    payload: res.data
  });
};

export const getPassengerTickets = passengerId => async dispatch => {
  const res = await axios.get(
    `http://localhost:8080/api/ticket/${passengerId}`
  );
  dispatch({
    type: GET_PASSENGER_TICKETS,
    payload: res.data
  });
};
