MysteryTrackingNumber
===

Given a tracking number, identify the Courier.

This is almost a port of jkeen's [Ruby
library](https://github.com/jkeen/tracking_number) but I simplified the
check digit algorithms and use a slightly different API.  Thank you,
Jeff Keen!

I will try to keep this project up to date with the Ruby one.  Please
open an issue/PR if this library is missing any Couriers.  PRs and
issues are gladly accepted.


Usage:
---

    TrackingNumber tn = TrackingNumber.parse("mytrackingnumber");

    if (tn != null) {
        tn.getCourierName();
        tn.getTrackingUrl();
        tn.trackingNumber;
    }


---

Developer notes:
---


### To add a new Courier:


- Subclass
  [`Courier`](https://github.com/adgaudio/MysteryTrackingNumber/blob/master/src/main/java/com/adgaudio/mysterytrackingnumber/Courier.java)
  and implement the abstract methods (see other couriers for examples).
    - You will need to figure out the regex that matches tracking
      numbers, and also specify a checkDigit algorithm that the carriers
      use to validate the regex.  Carriers post details about this
      online, but it can be hard to find.
- Add your new Courier to the list
  [TrackingNumber.couriers](https://github.com/adgaudio/MysteryTrackingNumber/blob/master/src/main/java/com/adgaudio/mysterytrackingnumber/TrackingNumber.java).
- Ensure that tests pass.
- Submit a PR.



### Information for tracking number "check digit" algorithms used by various couriers:

- This repo
- https://github.com/jkeen/tracking_number
- http://answers.google.com/answers/threadview/id/207899.html
- https://github.com/allisonBrenner/Validate-Tracking-Numbers/blob/master/ValidateTrackingNumbers.fs
