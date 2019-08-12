import { GET_TICKETS, GET_PASSENGER_TICKETS } from "../actions/types";

const initialState = {
  tickets: [],
  ticket: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TICKETS:
      return {
        ...state,
        tickets: action.payload
      };
    case GET_PASSENGER_TICKETS:
      return {
        ...state,
        tickets: action.payload
      };

    default:
      return state;
  }
}
