import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { loginPassenger } from "../../actions/passengersActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class SignIn extends Component {
  constructor() {
    super();
    this.state = {
      login: "",
      password: "",
      mappedErrors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSignInSubmit = this.onSignInSubmit.bind(this);
  }

  onSignInSubmit(e) {
    e.preventDefault();

    const loginRequest = {
      login: this.state.login,
      password: this.state.password
    };
    this.props.loginPassenger(loginRequest);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/home");
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.security.validToken) {
      this.props.history.push("/home");
    }
    if (nextProps.error) {
      this.setState({ mappedErrors: nextProps.error.mappedErrors });
    }
  }

  render() {
    const { mappedErrors } = this.state;

    return (
      <div
        className="site-blocks-cover overlay background-pic"
        data-stellar-background-ratio="0.5"
      >
        <div className="container">
          <div className="row align-items-center justify-content-center text-center">
            <div className="col-md-5">
              <div className="form-search-wrap p-2">
                <form onSubmit={this.onSignInSubmit}>
                  <div className="row align-items-center">
                    <div className="w-50 p-3 no-sm-border border-right">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="user" />
                        <input
                          type="text"
                          name="login"
                          value={this.state.login}
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.login
                          })}
                          required="required"
                          placeholder="Login"
                          onChange={this.onChange}
                        />
                      </div>
                      {mappedErrors.login && (
                        <span className="invalid-feedback">
                          {mappedErrors.login}
                        </span>
                      )}
                    </div>

                    <div className="w-50 p-3 no-sm-border">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="lock" />
                        <input
                          type="password"
                          name="password"
                          value={this.state.password}
                          required="required"
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.password
                          })}
                          placeholder="Password"
                          onChange={this.onChange}
                        />
                      </div>
                      {mappedErrors.password && (
                        <span className="invalid-feedback">
                          {mappedErrors.password}
                        </span>
                      )}
                    </div>

                    <div className="w-100 p-3 ml-auto text-center">
                      <input
                        type="submit"
                        className="btn btn-primary-o"
                        value="Sign In"
                      />
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

SignIn.propTypes = {
  loginPassenger: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security,
  error: state.error
});

export default connect(mapStateToProps, { loginPassenger })(SignIn);
