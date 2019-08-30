import { REMOVE_PASSENGER, GET_PASSENGERS_PAGE } from "../actions/types";

const initialState = {
  passengers: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PASSENGERS_PAGE:
      return {
        ...state,
        passengers: action.payload
      };
    case REMOVE_PASSENGER:
      return {
        ...state,
        passengers: state.passengers.filter(
          passenger => passenger.id !== action.payload
        )
      };
    default:
      return state;
  }
}
