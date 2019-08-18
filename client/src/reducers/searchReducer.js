import { SET_TRAIN_PARAM, SET_DIRECTION_PARAM } from "../actions/types";

const initialState = {
  directionParam: {},
  trainParam: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_DIRECTION_PARAM:
      return {
        ...state,
        directionParam: action.payload
      };
    case SET_TRAIN_PARAM:
      return {
        ...state,
        trainParam: action.payload
      };

    default:
      return state;
  }
}
