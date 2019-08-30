import axios from "axios";
import { GET_ERRORS, REMOVE_PASSENGER, GET_PASSENGERS_PAGE } from "./types";

export const getPassengersPage = pageNumber => async dispatch => {
  const res = await axios.get(`/api/passenger/page?page=${pageNumber}`);
  dispatch({
    type: GET_PASSENGERS_PAGE,
    payload: res.data
  });
};

export const countPassengersPages = () => async dispatch => {
  const res = await axios.get(`/api/passenger/page/count`);
  return res.data;
};

export const updatePassenger = passenger => async dispatch => {
  if (window.confirm("Do you really want to update passenger data?")) {
    try {
      const res = await axios.post("/api/passenger/update", passenger);
      dispatch({
        type: GET_ERRORS,
        payload: {}
      });
      return res.data;
    } catch (err) {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      });
    }
  }
};

export const removePassenger = passengerId => async dispatch => {
  if (window.confirm("Do you confirm removing this user?")) {
    await axios.delete(`/api/passenger/remove/${passengerId}`);
    dispatch({
      type: REMOVE_PASSENGER,
      payload: passengerId
    });
  }
};
