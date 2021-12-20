// SPDX-License-Identifier: MIT

pragma solidity 0.7.6;

import "@openzeppelin/contracts-upgradeable/token/ERC721/ERC721Upgradeable.sol";

contract FakeCreatorERC721 is ERC721Upgradeable {

    function mintDirect_without_CreatorsEvent(address to, uint tokenId) external {
        _safeMint(to, tokenId);
    }
}
