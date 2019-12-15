import React, { Component } from "react";
import { Link } from "react-router-dom";
import IcoMoon from "react-icomoon";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logoutPassenger } from "../../actions/passengersActions";

class Header extends Component {
  logout() {
    this.props.logoutPassenger();
    window.location.href = "/";
  }

  render() {
    const { validToken, passenger } = this.props.security;

    const passengerIsAuthenticated = () => {
      return (
        <React.Fragment>
          <li className="active">
            <Link to="/home">
              <span>Home</span>
            </Link>
          </li>
          <li>
            <Link to="/userlist">
              <span>Users</span>
            </Link>
          </li>
          <li>
            <Link to="/logout" onClick={this.logout.bind(this)}>
              <span>Log Out</span>
            </Link>
          </li>
        </React.Fragment>
      );
    };

    const passengerIsNotAuthenticated = () => {
      return (
        <React.Fragment>
          <li className="active">
            <Link to="/sign-in">
              <span>LogIn</span>
            </Link>
          </li>
          <li>
            <Link to="/sign-up">
              <span>Register</span>
            </Link>
          </li>
        </React.Fragment>
      );
    };

    let headerLinks;
    if (validToken && passenger) {
      headerLinks = passengerIsAuthenticated();
    } else headerLinks = passengerIsNotAuthenticated();

    return (
      <header className="site-navbar py-2 bg-white" role="banner">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-11 col-xl-2">
              <h1 className="mb-0 site-logo">
                <Link className="text-black h2 mb-0" to="/">
                  Ticket Office
                </Link>
              </h1>
            </div>
            <div className="col-12 col-md-10 d-none d-xl-block">
              <nav
                className="site-navigation position-relative text-right"
                role="navigation"
              >
                <ul className="site-menu js-clone-nav mr-auto d-none d-lg-block">
                  {headerLinks}
                  <li className="has-children">
                    <IcoMoon className="icon" icon="earth" />
                    <ul className="dropdown">
                      <li>
                        <Link to="#.html">
                          <span>en</span>
                        </Link>
                      </li>
                      <li>
                        <Link to="#.html">
                          <span>ru</span>
                        </Link>
                      </li>
                    </ul>
                  </li>
                </ul>
              </nav>
            </div>

            {/* <div
              className="d-inline-block d-xl-none ml-md-0 mr-auto py-3"
              //   style="position: relative; top: 3px;"
            >
              <a
                href="#"
                className="site-menu-toggle js-menu-toggle text-black"
              >
                <span className="icon-menu h3" />
              </a>
            </div> */}
          </div>
        </div>
      </header>
    );
  }
}

Header.propTypes = {
  logoutPassenger: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(mapStateToProps, { logoutPassenger })(Header);
