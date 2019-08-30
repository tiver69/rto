import React, { Component } from "react";
import DisplayUser from "./DisplayUser";
import {
  getPassengersPage,
  countPassengersPages
} from "../../actions/passengersActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class UserList extends Component {
  constructor() {
    super();
    this.state = {
      totalPages: 0,
      currentPage: 1
    };
  }

  componentDidMount() {
    // this.props.getPassengers();
    this.props.getPassengersPage(0);
    this.props.countPassengersPages().then(countPages => {
      this.setState({
        totalPages: countPages
      });
    });
  }

  createPages = () => {
    let pages = [];
    for (let i = 0; i < this.state.totalPages; i++) {
      if (i + 1 === this.state.currentPage) {
        pages.push(
          <li key={"coachNumber#" + i + 1}>
            <span>{i + 1}</span>
          </li>
        );
      } else {
        pages.push(
          <li key={"coachNumber#" + i + 1}>
            <button
              value={i}
              className="a-button"
              onClick={this.onPageClick.bind(this)}
            >
              {i + 1}
            </button>
          </li>
        );
      }
    }
    return pages;
  };

  onPageClick = number => {
    number.preventDefault();
    this.props.getPassengersPage(number.target.value);
    this.setState({
      currentPage: parseInt(number.target.value) + 1
    });
  };

  render() {
    const { passengers } = this.props.passenger;

    return (
      <React.Fragment>
        <div
          className="site-blocks-cover inner-page-cover overlay background-pic"
          data-stellar-background-ratio="0.5"
        >
          <div className="container">
            <div className="row align-items-center justify-content-center text-center">
              <div className="col index-home-message user">
                <div className="row justify-content-center">
                  <div className="col text-center">
                    <h1>USERS</h1>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* <!-- main section --> */}

        <div className="site-section">
          <div className="container">
            <div className="row">
              {/* <!-- user list section --> */}
              <div className="col">
                {passengers.map(passenger => (
                  <DisplayUser passenger={passenger} key={passenger.id} />
                ))}

                {/* <!-- pages --> */}
                <div className="col-12 mt-5 text-center">
                  <ul className="custom-pagination">{this.createPages()}</ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

UserList.propTypes = {
  passenger: PropTypes.object.isRequired,
  getPassengersPage: PropTypes.func.isRequired,
  countPassengersPages: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  passenger: state.passenger
});

export default connect(
  mapStateToProps,
  { getPassengersPage, countPassengersPages }
)(UserList);
