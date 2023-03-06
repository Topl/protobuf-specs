# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]


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
