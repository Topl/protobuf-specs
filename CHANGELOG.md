# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [v2.0.0-beta1] - 	

### Added
- Admin Rpc service.

## [Released]

## [v2.0.0-beta0] - Mon Nov 13 18:59:49 UTC 2023	

### Changed
- Change requerments for host known name
- Refactor Ratio message, moved to Quivr/shared
- Io Transaction includes includes merging and splitting statements


## [v2.0.0-alpha6] - Tue Sep 19 20:21:28 UTC 2023

### Added
- Refactor Ratio message, moved to Quivr/shared
- Io Transaction includes includes merging and splitting statements


## [v2.0.0-alpha5] - Tue Sep 19 20:21:28 UTC 2023	

### Added
 
- gRPC Health Check
- gRPC Fetch group and series policies
- TAM V2  models, minting, merging and splitting statements

### Changed

- Io Transaction includes minting asset statements
- Required GroupId and SeriesId on constructor tokens

## [v2.0.0-alpha4] - Tue Aug 29 17:36:07 UTC 2023	

### Added
 
- TAM V2 Access token models, FungibilityType QuantityDescriptorType

### Changed

- Io Transaction includes group and series policies


## [v2.0.0-alpha3] - Wed Aug 16 18:39:18 UTC 2023	

### Added
 
- TAM V2 Group Access token models
- TAM V2 Series Access token models

### Changed

- BlockBody message contains optional rewardTransactionId field
- FullBlockBody message contains optional rewardTransaction field
- Genus BlockData message replace `body` and `transactions` fields with a FullBody instance 

## [v2.0.0-alpha2] - Fri Jun 30 14:41:29 UTC 2023

### Changed

- Remove "Registration" Box Value
- Create co.topl.consensus.models.StakingRegistration message
- Create co.topl.consensus.models.ActiveStaker message
- Remove "stakingAddress" field from Topl Box Value
- Embed "StakingRegistration" in Topl Box Value
- Retrieve Epoch Data Stats, Node rpc `fetchEpochData`

## [v2.0.0-alpha1] - Mon Jun 12 13:53:06 UTC 2023

### Added
 
- all the below changes were included on Sonatype releases repository

### Added[ae3f01d]
- Retrieve Block Stats, Genus rpc `getBlockStats`

### Added[17a28eb]
- Retrieve Blockchain Size Stats, Genus rpc `getBlockchainSizeStats`
- Retrieve a stream of node's protocol configuration, Node rpc `fetchNodeConfig`
- Check the content of the node's mempool and return if a Transaction Id exists, Node rpc `currentMempoolContains`

### Changed[dfec2a0]
- Add NetworkMetricsService, Genus rpc
- Rename Address <- LockAddress

### Changed[36a496]
- Add transactionId field to IoTransaction message
- Add headerId field to BlockHeader message

### Changed[a06dd3]
- Remove 32/64 message types
- Add TransactionId message
- Add LockId message

### Changed[0b81c5]
- Create "StakingAddress" message, used in BlockHeader and Box Value

### Changed[2f145a]
- Delete legacy proto messages
- Simplify OperationalCertificate fields
- Replace "Token" Value with LVL and TOPL.  Added Registration Value.

### Changed[9a6db2]
- Create LockAddress, TransactionOutputAddress, and TransactionInputAddress
- Remove KnownIdentifier

### Changed[7d2ba5c]
- Quivr4s model, Witness value len rule 64 validation.

### Changed[c6f0253]
- Quivr4s constraints validations.

### Changed[28aa03e]
- Genus protobuf migration

### Changed[c226e4c]
- Node models: Block, and FullBlockBody constraints validations.

### Changed[c920f90]
- Consensus OperationalCertificate includes VerificationKeyEd25519, SecretKeyEd25519, SignatureEd25519 with constraints validations.

### Changed[53c5f3a]
- Node services Rpc constraints validations.

### Changed[f2b8a87]
- Added the required PB scala validation rules to some IoTransaction models
- Added scala PB validation rules to all brambl models

### Changed[948dc20]
- Consensus Block Header constraints validations

### Changed[437d117]
- Consensus SlotData constraints validations

### Changed
- Moved Topl domain protos to `proto` directory

### Changed
- Use protoc-gen-validate in EligibilityCertificate model

### Changed
- [BN-813](https://topl.atlassian.net/browse/BN-813) bifrost_rpc.proto Synchronization Traversal Response Status returns applied and unapplied Block Ids

### Added
- co.topl.consensus.models.BlockId message

### Fixed
- Commitment32/Commitment64 root repeated

### Fixed
- Event Root ADT error

### Fixed
- quivr Digest proposition recursive self-reference

### Added 
- co.topl.node.models.Block message type

### Changed
- bifrost_rpc.proto now references the legacy Transaction message
