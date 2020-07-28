# mailchimp-downloader

Download my mailchimp exports and save it onto local filesystem periodically

## Setup
- Environment variables to be able to talk to Mailchimp and AWS:
  - `MAILCHIMPKEY`
  - `MAILCHIMPLISTID`
  - `AWS_ACCESS_KEY_ID`
  - `AWS_SECRET_ACCESS_KEY`
  - `AWS_REGION`
  - `MCBACKUPBUCKET`

## Usage

    lein run

## Resources
- [Mailchimp Export API](https://mailchimp.com/developer/guides/how-to-use-the-export-api/)
- [Setup an API key](https://mailchimp.com/help/about-api-keys/)
- [aws-clj](https://github.com/cognitect-labs/aws-api)

## TODO
- Perhaps remove the csv files from disk once saved in S3
- Maybe unnecessary to save in file?

## License

Copyright © 2020 Takahisa Hasegawa

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
