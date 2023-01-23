# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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
