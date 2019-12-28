-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Lun 20 Février 2017 à 16:22
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `mediatheque`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonne`
--

CREATE TABLE `abonne` (
  `id_abonne` int(11) NOT NULL,
  `nom` varchar(30) COLLATE utf8_bin NOT NULL,
  `prenom` varchar(30) COLLATE utf8_bin NOT NULL,
  `annee_naissance` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `abonne`
--

INSERT INTO `abonne` (`id_abonne`, `nom`, `prenom`, `annee_naissance`) VALUES
(1, 'Boudissa', 'Soffiane', 1986),
(2, 'Enfant', 'un', 2002),
(3, 'Adulte', 'deux', 1975),
(4, 'Enfant', 'deux', 2001);

-- --------------------------------------------------------

--
-- Structure de la table `counter`
--

CREATE TABLE `counter` (
  `name` varchar(20) COLLATE utf8_bin NOT NULL,
  `value` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `counter`
--

INSERT INTO `counter` (`name`, `value`) VALUES
('DVD', 9),
('Periodique', 4),
('Livre', 8);

-- --------------------------------------------------------

--
-- Structure de la table `document`
--

CREATE TABLE `document` (
  `id_document` varchar(11) COLLATE utf8_bin NOT NULL,
  `titre` varchar(50) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `document`
--

INSERT INTO `document` (`id_document`, `titre`) VALUES
('dvd1', 'Le vent se leve'),
('dvd2', 'Le pianiste'),
('dvd3', 'Vertigo'),
('dvd4', 'Alien'),
('dvd5', 'Le retour du roi'),
('dvd6', 'Virgin suicide'),
('dvd7', 'Sleepy Hollow'),
('dvd8', 'Les 4 fantastiques'),
('liv1', 'Le capital'),
('liv2', 'Da Vinci Code'),
('liv3', 'Le Couloir des Tenebres'),
('liv4', 'Les hommes revoltes'),
('liv5', 'Les fleurs du mal'),
('liv6', 'Les miserables'),
('liv7', 'Don Quichotte'),
('liv8', 'La divine comedie'),
('per1', 'AutoPlus'),
('per2', 'Challenge'),
('per3', 'Le Monde Diplomatique'),
('per4', 'Okapi');

-- --------------------------------------------------------

--
-- Structure de la table `dvd`
--

CREATE TABLE `dvd` (
  `id_document` varchar(11) COLLATE utf8_bin NOT NULL,
  `realisateur` varchar(50) COLLATE utf8_bin NOT NULL,
  `annee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `dvd`
--

INSERT INTO `dvd` (`id_document`, `realisateur`, `annee`) VALUES
('dvd1', 'Ken Loach', 2007),
('dvd2', 'Roman Polanski', 2003),
('dvd3', 'Alfred Hitchcock', 1958),
('dvd4', 'Ridley Scott', 1979),
('dvd5', 'Peter Jackson', 2003),
('dvd6', 'Sofia Coppola', 1999),
('dvd7', 'Tim Burton', 1999),
('dvd8', 'Tim Story', 2005);

-- --------------------------------------------------------

--
-- Structure de la table `emprunt`
--

CREATE TABLE `emprunt` (
  `id_document` varchar(11) COLLATE utf8_bin NOT NULL,
  `id_abonne` int(11) NOT NULL,
  `dateEmprunt` date DEFAULT NULL,
  `dateRetour` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE `livre` (
  `id_document` varchar(11) COLLATE utf8_bin NOT NULL,
  `auteur` varchar(50) COLLATE utf8_bin NOT NULL,
  `editeur` varchar(50) COLLATE utf8_bin NOT NULL,
  `annee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `livre`
--

INSERT INTO `livre` (`id_document`, `auteur`, `editeur`, `annee`) VALUES
('liv1', 'Karl Marx', 'Folio', 1867),
('liv2', 'Dan Brown', 'JC lattes', 2003),
('liv3', 'Anne Perry', '10/18', 2016),
('liv4', 'Emmanuelle Jousse', 'Fayard', 2017),
('liv5', 'Charles Baudelaire', 'Le Livre de Poche', 1857),
('liv6', 'Victor Hugo', 'Le Livre de Poche', 1862),
('liv7', 'Miguel de Cervantes', 'Folio classique', 1605),
('liv8', 'Dante', 'Flammarion', 1555);

-- --------------------------------------------------------

--
-- Structure de la table `periodique`
--

CREATE TABLE `periodique` (
  `id_document` varchar(11) COLLATE utf8_bin NOT NULL,
  `theme` varchar(50) COLLATE utf8_bin NOT NULL,
  `periodicite` varchar(50) COLLATE utf8_bin NOT NULL,
  `numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `periodique`
--

INSERT INTO `periodique` (`id_document`, `theme`, `periodicite`, `numero`) VALUES
('per1', 'Automobile', 'Hebdomadaire', 48),
('per2', 'Economie', 'Hebdomadaire', 5),
('per3', 'Geopolitique', 'Mensuel', 175),
('per4', 'Jeunesse', 'Bimensuel', 1203);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `abonne`
--
ALTER TABLE `abonne`
  ADD PRIMARY KEY (`id_abonne`);

--
-- Index pour la table `counter`
--
ALTER TABLE `counter`
  ADD PRIMARY KEY (`name`);

--
-- Index pour la table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`id_document`);

--
-- Index pour la table `dvd`
--
ALTER TABLE `dvd`
  ADD PRIMARY KEY (`id_document`);

--
-- Index pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD PRIMARY KEY (`id_document`,`id_abonne`),
  ADD KEY `id_abonne` (`id_abonne`);

--
-- Index pour la table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`id_document`);

--
-- Index pour la table `periodique`
--
ALTER TABLE `periodique`
  ADD PRIMARY KEY (`id_document`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `abonne`
--
ALTER TABLE `abonne`
  MODIFY `id_abonne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `dvd`
--
ALTER TABLE `dvd`
  ADD CONSTRAINT `DVD_DOCUMENT_PK` FOREIGN KEY (`id_document`) REFERENCES `document` (`id_document`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `emprunt_ibfk_1` FOREIGN KEY (`id_document`) REFERENCES `document` (`id_document`),
  ADD CONSTRAINT `emprunt_ibfk_2` FOREIGN KEY (`id_abonne`) REFERENCES `abonne` (`id_abonne`);

--
-- Contraintes pour la table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `LIVRE_DOCUMENT_PK` FOREIGN KEY (`id_document`) REFERENCES `document` (`id_document`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `periodique`
--
ALTER TABLE `periodique`
  ADD CONSTRAINT `PERIODIQUE_DOCUMENT_PK` FOREIGN KEY (`id_document`) REFERENCES `document` (`id_document`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
