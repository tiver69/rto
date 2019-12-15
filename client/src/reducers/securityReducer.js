import { LOGIN_USER } from "../actions/types";

const initialState = {
  validToken: false,
  passenger: {}
};

const booleanActionPayload = payload => {
  if (payload) {
    return true;
  } else {
    return false;
  }
};

export default function(state = initialState, action) {
  switch (action.type) {
    case LOGIN_USER:
      return {
        ...state,
        validToken: booleanActionPayload(action.payload),
        passenger: action.payload
      };

    default:
      return state;
  }
}
