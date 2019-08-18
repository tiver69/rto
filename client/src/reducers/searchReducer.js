import { SET_TRAIN_PARAM } from "../actions/types";

const initialState = {
  trainParam: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_TRAIN_PARAM:
      return {
        ...state,
        trainParam: action.payload
      };

    default:
      return state;
  }
}
