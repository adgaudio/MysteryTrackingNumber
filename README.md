MysteryTrackingNumber
===

Given a tracking number, identify the Courier.

This is a Java library that wraps JSON data from [a shared
repository](https://github.com/jkeen/tracking_number_data).  It is
originally inspired by jkeen's [Ruby
library](https://github.com/jkeen/tracking_number_data).  Thank you,
Jeff Keen!

Please open an issue/PR if this library is missing any Couriers.  PRs and
issues are gladly accepted.


Usage:
---

    TrackingNumber tn = TrackingNumber.parse("mytrackingnumber");

    tn.isCourierRecognized();
    tn.getCourierName();
    tn.getTrackingUrl();
    tn.trackingNumber;


---

Developer notes:
---


### To add a new Courier:


  - Please create a PR in https://github.com/jkeen/tracking_number_data
  - Then create a PR in this repo that bumps the tracking_number_data
    submodule commit (or you can open an issue asking me to do it).



### Information for tracking number "check digit" algorithms used by various couriers:

- https://github.com/jkeen/tracking_number_data
- https://github.com/jkeen/tracking_number
