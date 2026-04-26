import { exec } from "child_process";
import { readFile } from "fs/promises";

// ✅ Read package.json (ESM way)
const packageJson = JSON.parse(
  await readFile(new URL("./package.json", import.meta.url))
);

const dependencies = [
  ...Object.keys(packageJson.dependencies || {}),
  ...Object.keys(packageJson.devDependencies || {})
];

const checkPackage = (pkg) => {
  return new Promise((resolve) => {
    exec(`npm view ${pkg}`, (error) => {
      if (error) {
        resolve({ pkg, status: "NOT_FOUND" });
      } else {
        resolve({ pkg, status: "FOUND" });
      }
    });
  });
};

console.log("🔍 Checking packages in registry...\n");

const results = await Promise.all(dependencies.map(checkPackage));

const found = [];
const notFound = [];

results.forEach(({ pkg, status }) => {
  if (status === "FOUND") found.push(pkg);
  else notFound.push(pkg);
});

console.log("✅ FOUND PACKAGES:");
found.forEach(pkg => console.log(`  ✔ ${pkg}`));

console.log("\n❌ NOT FOUND IN REGISTRY:");
notFound.forEach(pkg => console.log(`  ✖ ${pkg}`));

console.log("\n📊 SUMMARY:");
console.log(`Total: ${dependencies.length}`);
console.log(`Found: ${found.length}`);
console.log(`Missing: ${notFound.length}`);

if (notFound.length > 0) {
  process.exit(1);
}