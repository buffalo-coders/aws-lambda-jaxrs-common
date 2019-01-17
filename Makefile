###
# #%L
# common
# %%
# Copyright (C) 2019 Buffalo Coders
# %%
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# #L%

default: install

.PHONY: clean
clean:
	@mvn clean

.PHONY: deploy
deploy: gpg-init
	@mvn --activate-profiles sonatype-oss-release deploy

.PHONY: display-updates
display-updates:
	@mvn -Dmaven.version.rules=https://raw.githubusercontent.com/buffalo-coders/parent/master/src/main/resources/versions-maven-plugin.rules.xml \
		versions:display-dependency-updates \
		versions:display-parent-updates \
		versions:display-plugin-updates \
		versions:display-property-updates

.PHONY: fix
fix:
	${MAKE} fix-crlf fix-license fix-notices

.PHONY: fix-crlf
fix-crlf: no-git-changes
	@mvn --threads 1 --activate-profiles fix-crlf clean
	-@git commit -a -m 'make fix-crlf'

.PHONY: fix-license
fix-license: no-git-changes
	@mvn --threads 1 --activate-profiles fix-license clean
	-@git commit -a -m 'make fix-license'

.PHONY: fix-notices
fix-notices: no-git-changes
	@mvn --threads 1 --activate-profiles fix-notices clean
	-@git commit -a -m 'make fix-notices'

.PHONY: gpg-init
gpg-init:
	@echo "Setting up GPG."
	$(eval TMP := $(shell mktemp))
	touch $(TMP)
	gpg -ab $(TMP)
	rm $(TMP) $(TMP).asc

.PHONY: install
install:
	@mvn install

.PHONY: release
release: gpg-init no-git-changes
	@mvn --batch-mode --activate-profiles sonatype-oss-release release:prepare release:perform

no-git-changes:
	@git diff --quiet --exit-code
	@git diff --quiet --exit-code --cached
