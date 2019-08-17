import { SET_TRAIN_PARAM } from "./types";

export const setSearchParam = param => async dispatch => {
  dispatch({
    type: SET_TRAIN_PARAM,
    payload: param
  });
};
