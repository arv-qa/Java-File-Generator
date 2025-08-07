# CI/CD Pipeline Documentation

## Overview

This project uses GitHub Actions for Continuous Integration and Continuous Deployment (CI/CD). The pipeline automatically builds, tests, and releases the Java File Generator application across multiple platforms.

## Pipeline Structure

### 1. Main CI/CD Pipeline (`ci-cd.yml`)

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` branch
- Release publications

**Jobs:**
- **Test Suite**: Multi-platform testing across Java 8, 11, and 17
- **Code Quality**: Code coverage analysis with JaCoCo
- **Build**: Application packaging and artifact creation
- **Security Scan**: OWASP dependency vulnerability scanning
- **Release**: Automated release creation and asset upload

### 2. Pull Request Check (`pr-check.yml`)

**Triggers:**
- Pull requests to `main` or `develop` branches

**Features:**
- Fast validation of PR changes
- Build verification
- JAR creation testing
- Automated PR comments with build status
- Code style checks

### 3. Release Pipeline (`release.yml`)

**Triggers:**
- Git tags matching `v*` pattern (e.g., `v1.0.0`)

**Features:**
- Multi-platform builds (Ubuntu, Windows, macOS)
- Automatic version management
- Changelog generation
- Release asset creation
- Source code packaging

## Build Matrix

### Supported Platforms
- **Ubuntu Latest** (Primary)
- **Windows Latest**
- **macOS Latest**

### Java Versions
- **Java 8** (Primary target)
- **Java 11** (Compatibility)
- **Java 17** (Future compatibility)

## Artifacts Generated

### 1. Application JAR
- **File**: `java-file-generator-{version}.jar`
- **Type**: Executable fat JAR with all dependencies
- **Usage**: `java -jar java-file-generator-{version}.jar`

### 2. Source JAR
- **File**: `java-file-generator-{version}-sources.jar`
- **Type**: Source code archive
- **Usage**: For developers and IDE integration

### 3. Test Reports
- **Format**: JUnit XML and HTML
- **Coverage**: JaCoCo code coverage reports
- **Location**: Available as workflow artifacts

### 4. Security Reports
- **Tool**: OWASP Dependency Check
- **Format**: HTML report
- **Content**: Vulnerability analysis of dependencies

## Quality Gates

### 1. Code Quality
- **Tool**: JaCoCo for code coverage
- **Integration**: Codecov for coverage reporting
- **Threshold**: Configurable coverage requirements

### 2. Security Scanning
- **Tool**: OWASP Dependency Check
- **Frequency**: Every build
- **Action**: Reports vulnerabilities in dependencies

### 3. Multi-Platform Testing
- **Platforms**: Linux, Windows, macOS
- **Java Versions**: 8, 11, 17
- **Tests**: Unit tests and integration tests

## Workflow Triggers

### Automatic Triggers
```yaml
# Push to main branches
on:
  push:
    branches: [ main, develop ]

# Pull requests
on:
  pull_request:
    branches: [ main ]

# Release tags
on:
  push:
    tags: [ 'v*' ]
```

### Manual Triggers
- Workflows can be manually triggered from GitHub Actions tab
- Release workflow can be triggered by creating a new tag

## Environment Variables

### Global Settings
```yaml
env:
  JAVA_VERSION: '8'          # Primary Java version
  MAVEN_OPTS: '-Xmx1024m'    # Maven memory settings
```

### Secrets Required
- `GITHUB_TOKEN`: Automatically provided by GitHub
- Additional secrets can be added for external integrations

## Caching Strategy

### Maven Dependencies
```yaml
- name: Cache Maven dependencies
  uses: actions/cache@v3
  with:
    path: ~/.m2
    key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
```

**Benefits:**
- Faster build times
- Reduced network usage
- Consistent dependency versions

## Release Process

### 1. Automatic Releases
```bash
# Create and push a tag
git tag v1.0.1
git push origin v1.0.1
```

### 2. Release Assets
- Executable JAR file
- Source code JAR
- Auto-generated changelog
- Multi-platform build verification

### 3. Version Management
- Automatic version extraction from git tags
- POM version updates during release
- Semantic versioning support

## Monitoring and Notifications

### 1. Build Status
- GitHub status checks on PRs
- Automated PR comments with build results
- Workflow status badges (can be added to README)

### 2. Failure Handling
- Failed builds prevent merging
- Detailed error logs in workflow runs
- Artifact preservation for debugging

## Local Development

### Running CI Checks Locally
```bash
# Run tests
mvn clean test

# Generate coverage report
mvn clean test jacoco:report

# Security scan
mvn org.owasp:dependency-check-maven:check

# Build application
mvn clean package
```

### Pre-commit Validation
```bash
# Validate project
mvn validate

# Compile code
mvn compile

# Run all checks
mvn clean verify
```

## Troubleshooting

### Common Issues

1. **Test Failures**
   - Check test logs in workflow artifacts
   - Run tests locally: `mvn test`
   - Verify JavaFX dependencies for headless testing

2. **Build Failures**
   - Check Maven logs
   - Verify POM configuration
   - Check dependency conflicts

3. **Security Scan Issues**
   - Review OWASP report
   - Update vulnerable dependencies
   - Add suppressions if needed

### Debug Steps
1. Check workflow logs in GitHub Actions
2. Download and examine artifacts
3. Run builds locally with same Java version
4. Verify environment variables and secrets

## Performance Metrics

### Typical Build Times
- **Test Suite**: 2-5 minutes per platform/Java version
- **Build**: 1-2 minutes
- **Security Scan**: 2-3 minutes
- **Total Pipeline**: 5-10 minutes

### Resource Usage
- **Memory**: 1GB Maven heap
- **Storage**: ~500MB for dependencies cache
- **Network**: Minimal with caching enabled

## Future Enhancements

### Planned Improvements
- [ ] SonarQube integration for code quality
- [ ] Docker image builds
- [ ] Performance benchmarking
- [ ] Automated dependency updates
- [ ] Integration testing with UI automation
- [ ] Deployment to package repositories

### Configuration Options
- Configurable test timeouts
- Custom Maven profiles
- Environment-specific builds
- Feature flag support
