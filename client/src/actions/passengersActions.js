import axios from "axios";
import {
  GET_MAPPED_ERRORS,
  GET_STRING_ERROR,
  REMOVE_PASSENGER,
  GET_PASSENGERS_PAGE,
  LOGIN_USER
} from "./types";
import setJwtToken from "../util/setJwtToken";
import jwt_decode from "jwt-decode";

export const getPassengersPage = pageNumber => async dispatch => {
  const res = await axios.get(`/api/passenger/page?page=${pageNumber}`);
  dispatch({
    type: GET_PASSENGERS_PAGE,
    payload: res.data
  });
};

export const registerPassenger = (newPassenger, history) => async dispatch => {
  try {
    await axios.post("/api/passenger/register", newPassenger);
    history.push("/sign-in");
    dispatch({
      type: GET_MAPPED_ERRORS,
      payload: {}
    });
  } catch (err) {
    if (err.response)
      //TO_DO: check why this if is requered here
      dispatch({
        type: GET_MAPPED_ERRORS,
        payload: err.response.data
      });
  }
};

//   post login request
//   extrct token from res.data
//   store token at lockal storage
//   set token to header
//   decode token in react
//   dispatch to security reduser
export const loginPassenger = loginRequest => async dispatch => {
  try {
    const res = await axios.post("/api/passenger/login", loginRequest);
    const { token } = res.data;
    localStorage.setItem("jwtToken", token);
    setJwtToken(token);
    const decoded = jwt_decode(token);
    dispatch({
      type: LOGIN_USER,
      payload: decoded
    });
  } catch (err) {
    dispatch({
      type: GET_MAPPED_ERRORS,
      payload: err.response.data
    });
  }
};

export const logoutPassenger = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJwtToken(false);
  dispatch({
    type: LOGIN_USER,
    payload: {}
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
