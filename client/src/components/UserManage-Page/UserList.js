import React, { Component } from "react";
import DisplayUser from "./DisplayUser";
import { getPassengersPage } from "../../actions/passengersActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class UserList extends Component {
  constructor() {
    super();
    this.state = {
      currentPage: 1
    };
  }

  componentDidMount() {
    this.props.getPassengersPage(0);
  }

  createPages = totalPages => {
    let pages = [];
    for (let i = 0; i < totalPages; i++) {
      if (i + 1 === this.state.currentPage) {
        pages.push(
          <li key={"pageNumber#" + i + 1}>
            <span>{i + 1}</span>
          </li>
        );
      } else {
        pages.push(
          <li key={"pageNumber#" + i + 1}>
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
                {passengers.length !== 0 &&
                  passengers.currentPage.map(passenger => (
                    <DisplayUser passenger={passenger} key={passenger.id} />
                  ))}

                {/* <!-- pages --> */}
                <div className="col-12 mt-5 text-center">
                  <ul className="custom-pagination">
                    {this.createPages(passengers.totalPages)}
                  </ul>
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
  getPassengersPage: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  passenger: state.passenger
});

export default connect(mapStateToProps, {
  getPassengersPage
})(UserList);
