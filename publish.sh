git checkout gh-pages || exit $?

# Clear out the old files:
rm -rf javadoc/*
# Replace them with new files and commit them:
cp -R build/docs/javadoc/ javadoc \
&& git add javadoc \
&& git commit -a -m "generated javadoc"
ERROR=$?

git checkout master || exit $?
[ $ERROR -eq 0 ] || exit $ERROR
git push origin master || exit $?
git push origin gh-pages || exit $?