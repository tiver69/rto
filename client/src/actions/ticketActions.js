import axios from "axios";
import { GET_PASSENGER_TICKETS } from "./types";

export const getPassengerTickets = passengerId => async dispatch => {
  const res = await axios.get(
    `http://localhost:8080/api/ticket?passengerId=${passengerId}`
  );
  dispatch({
    type: GET_PASSENGER_TICKETS,
    payload: res.data
  });
};
