import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { Link } from "react-router-dom";

class Ticket extends Component {
  render() {
    return (
      <div className="d-block d-md-flex listing-horizontal">
        <div className="img d-block ticket-active-background-pic">
          <span className="category">
            COACH - 5
            <br />
            PLACE - 4
            <br />
            PRICE - 164₴
            <br />
          </span>
        </div>
        {/* <div className="img d-block ticket-history-background-pic">
             <span className="category">
                   <fmt:message key="ticket.coach"/> - <c:out value="${ticket.getTicket().getTrainCoach().getNumber()}"/><br/>
                   <fmt:message key="ticket.place"/> - <c:out value="${ticket.getTicket().getPlace()}"/><br/>
                   <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/>₴<br/>
             </span>
           </div> */}

        <div className="lh-content">
          <Link
            to="/todo"
            href="returnTicket?ticketId=${ticket.getTicket().getId()}"
            className="bookmark"
            method="delete"
          >
            <span>
              <IcoMoon className="icon" icon="blocked" />
              Return
            </span>
          </Link>

          <h3>Zaporizhzhya 1 - Im. T. Shevchenka</h3>

          <p>
            <IcoMoon className="icon" icon="road" /> Train number #72
          </p>

          <p>
            <IcoMoon className="icon" icon="clock" /> Departure - 2019-05-15
            18:20:00.0
          </p>

          <p>
            <IcoMoon className="icon" icon="clock" /> Arrival - 2019-05-16
            02:55:00.0
          </p>
        </div>
      </div>
    );
  }
}

export default Ticket;
