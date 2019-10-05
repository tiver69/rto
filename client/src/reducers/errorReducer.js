import { GET_STRING_ERROR, GET_MAPPED_ERRORS } from "../actions/types";

const initialState = { stringError: "", mappedErrors: {} };

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_STRING_ERROR:
      return {
        ...state,
        stringError: action.payload
      };
    case GET_MAPPED_ERRORS:
      return {
        ...state,
        mappedErrors: action.payload
      };

    default:
      return state;
  }
}
