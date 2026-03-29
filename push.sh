#!/bin/bash
read -s -p "Token GitHub: " TOKEN
echo ""
cd /root/dentapp-android
git push "https://${TOKEN}@github.com/andresgt1989/dentapp-android.git" main --set-upstream 2>&1
unset TOKEN
