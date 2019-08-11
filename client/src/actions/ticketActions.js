import axios from "axios";
import { GET_TICKETS } from "./types";
import { async } from "q";

export const getTickets = () => async dispatch => {
  const res = await axios.get("http://localhost:8080/api/ticket/all");
  dispatch({
    type: GET_TICKETS,
    payload: res.data
  });
};
