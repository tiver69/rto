import { GET_STRING_ERROR, GET_MAPPED_ERRORS } from "./types";

export const cleanErrors = () => async dispatch => {
  dispatch({
    type: GET_STRING_ERROR,
    payload: ""
  });
  dispatch({
    type: GET_MAPPED_ERRORS,
    payload: {}
  });
};
