import axios from "axios";
import {
  GET_MAPPED_ERRORS,
  GET_STRING_ERROR,
  REMOVE_PASSENGER,
  GET_PASSENGERS_PAGE
} from "./types";

export const getPassengersPage = pageNumber => async dispatch => {
  const res = await axios.get(`/api/passenger/page?page=${pageNumber}`);
  dispatch({
    type: GET_PASSENGERS_PAGE,
    payload: res.data
  });
};

export const updatePassenger = passenger => async dispatch => {
  if (window.confirm("Do you really want to update passenger data?")) {
    try {
      const res = await axios.post("/api/passenger/update", passenger);
      dispatch({
        type: GET_MAPPED_ERRORS,
        payload: {}
      });
      dispatch({
        type: GET_MAPPED_ERRORS,
        payload: {}
      });
      return res.data;
    } catch (err) {
      dispatch({
        type: GET_MAPPED_ERRORS,
        payload: err.response.data
      });
    }
  }
};

export const removePassenger = passengerId => async dispatch => {
  if (window.confirm("Do you confirm removing this user?")) {
    try {
      await axios.delete(`/api/passenger/remove/${passengerId}`);
      dispatch({
        type: REMOVE_PASSENGER,
        payload: passengerId
      });
    } catch (err) {
      dispatch({
        type: GET_STRING_ERROR,
        payload: err.response.data
      });
    }
  }
};
