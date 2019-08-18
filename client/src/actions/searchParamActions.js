import { SET_TRAIN_PARAM, SET_DIRECTION_PARAM } from "./types";

export const setDirectionSearchParam = param => async dispatch => {
  dispatch({
    type: SET_DIRECTION_PARAM,
    payload: param
  });
};

export const setTeainSearchParam = param => async dispatch => {
  dispatch({
    type: SET_TRAIN_PARAM,
    payload: param
  });
};
