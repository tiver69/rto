import { GET_PASSENGER_TICKETS, SAVE_NEW_TICKET } from "../actions/types";

const initialState = {
  tickets: [],
  ticket: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PASSENGER_TICKETS:
      return {
        ...state,
        tickets: action.payload
      };
    case SAVE_NEW_TICKET:
      return {
        ...state,
        ticket: action.payload
      };

    default:
      return state;
  }
}
