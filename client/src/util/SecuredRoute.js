import React from "react";
import { Route, Redirect } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";

const SecuredRoute = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={props =>
      security.validToken === true ? (
        <Component {...props} />
      ) : (
        <Redirect to="/sign-in" />
      )
    }
  />
);

SecuredRoute.propTypes = {
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps)(SecuredRoute);
